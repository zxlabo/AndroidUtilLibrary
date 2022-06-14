package com.labo.lib.tool.log;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.labo.lib.tool.utils.AppGlobals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * tips：
 * 1、BlockingQueue的使用，防止频繁的创建线程；
 * 2、线程同步；
 * 2、文件操作，BufferedWriter的应用；
 */
public class HiFilePrinter extends HiLogPrinter {

    private static volatile HiFilePrinter instance;
    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
    private final String logPath;
    private final long retentionTime;
    private LogWriter writer;
    private volatile PrintWorker worker;
    private static final String LOG_FILE = File.separator + "log";
    private List<String> whiteList;

    /**
     * 创建HiFilePrinter
     */
    public static HiFilePrinter getInstance(List<String> whiteList,@NonNull HiLogConfig config ) {
        return getInstance(null, 0,  whiteList,config);
    }

    public void setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
    }

    /**
     * 创建HiFilePrinter
     *
     * @param logPath       log保存路径，如果是外部路径需要确保已经有外部存储的读写权限
     * @param retentionTime log文件的有效时长，单位毫秒，<=0表示一直有效
     */
    public static HiFilePrinter getInstance(String logPath, long retentionTime, List<String> whiteList, @NonNull HiLogConfig config) {
        if (instance == null) {
            synchronized (HiFilePrinter.class) {
                if (instance == null) {
                    if (TextUtils.isEmpty(logPath)) {
                        logPath = AppGlobals.INSTANCE.getContext().getFilesDir().getAbsolutePath() + LOG_FILE;
                    }
                    instance = new HiFilePrinter(logPath, retentionTime, whiteList, config);
                }
            }
        }
        return instance;
    }

    private HiFilePrinter(String logPath, long retentionTime, List<String> whiteList, HiLogConfig config) {
        super(config);
        this.logPath = logPath;
        this.retentionTime = retentionTime;
        this.writer = new LogWriter();
        this.worker = new PrintWorker();
        this.whiteList = whiteList;
        cleanExpiredLog();
    }

    @Override
    protected String formatMsg(Object[] contents) {
        return LogMsgUtil.parseBody(contents, config);
    }

    @Override
    void print(int level, String tag, @NonNull String contents) {
        long timeMillis = System.currentTimeMillis();
        if (!worker.isRunning()) {
            worker.start();
        }
        if (whiteList.contains(tag)){
            worker.put(new HiLogMo(timeMillis, level, tag, contents));
        }
    }


    private void doPrint(HiLogMo logMo) {
        String lastFileName = writer.getPreFileName();
        if (lastFileName == null) {
            String newFileName = genFileName();
            if (writer.isReady()) {
                writer.close();
            }
            if (!writer.ready(newFileName)) {
                return;
            }
        }
        writer.append(logMo.flattenedLog());
    }

    private String genFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 清除过期log
     */
    private void cleanExpiredLog() {
        if (retentionTime <= 0) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        File logDir = new File(logPath);
        File[] files = logDir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (currentTimeMillis - file.lastModified() > retentionTime) {
                file.delete();
            }
        }
    }

    private class PrintWorker implements Runnable {

        private BlockingQueue<HiLogMo> logs = new LinkedBlockingQueue<>();

        private volatile boolean running;

        /**
         * 将log放入打印队列
         *
         * @param log 要被打印的log
         */
        void put(HiLogMo log) {
            try {
                logs.put(log);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * 判断工作线程是否还在运行中
         *
         * @return true 在运行
         */
        boolean isRunning() {
            synchronized (this) {
                return running;
            }
        }

        /**
         * 启动工作线程
         */
        void start() {
            synchronized (this) {
                EXECUTOR.execute(this);
                running = true;
            }
        }

        @Override
        public void run() {
            HiLogMo log;
            try {
                while (true) {
                    log = logs.take();
                    doPrint(log);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                synchronized (this) {
                    running = false;
                }
            }
        }
    }


    /**
     * 基于BufferedWriter将log写入文件
     */
    private class LogWriter {

        private String preFileName;
        private File logFile;
        private BufferedWriter bufferedWriter;

        boolean isReady() {
            return bufferedWriter != null;
        }

        String getPreFileName() {
            return preFileName;
        }

        /**
         * log写入前的准备操作
         *
         * @param newFileName 要保存log的文件名
         * @return true 表示准备就绪
         */
        boolean ready(String newFileName) {
            preFileName = newFileName;
            logFile = new File(logPath, newFileName);
            // 当log文件不存在时创建log文件
            if (!logFile.exists()) {
                try {
                    File parent = logFile.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    logFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    preFileName = null;
                    logFile = null;
                    return false;
                }
            }

            try {
                bufferedWriter = new BufferedWriter(new FileWriter(logFile, true));
            } catch (Exception e) {
                e.printStackTrace();
                preFileName = null;
                logFile = null;
                return false;
            }
            return true;
        }

        /**
         * 关闭bufferedWriter
         */
        boolean close() {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    bufferedWriter = null;
                    preFileName = null;
                    logFile = null;
                }
            }
            return true;
        }

        /**
         * 将log写入文件
         *
         * @param flattenedLog 格式化后的log
         */
        void append(String flattenedLog) {
            try {
                bufferedWriter.write(flattenedLog);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
