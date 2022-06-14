package com.labo.lib.tool.log;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Tips:
 * 1. 打印堆栈信息
 * 2. File输出
 * 3. 模拟控制台
 */
public class HiLog {


    public static void v(String tag, Object... contents) {
        log(HiLogType.V, tag, contents);
    }


    public static void d(String tag, Object... contents) {
        log(HiLogType.D, tag, contents);
    }


    public static void i(String tag, Object... contents) {
        log(HiLogType.I, tag, contents);
    }


    public static void w(String tag, Object... contents) {
        log(HiLogType.W, tag, contents);
    }


    public static void e(String tag, Object... contents) {
        log(HiLogType.E, tag, contents);
    }


    public static void a(String tag, Object... contents) {
        log(HiLogType.A, tag, contents);
    }


    public static void log(@HiLogType.TYPE int type, @NonNull String tag, Object... contents) {
        List<HiLogPrinter> printers = HiLogManager.getInstance().getPrinters();
        if (printers == null) {
            return;
        }
        //打印log
        for (HiLogPrinter printer : printers) {
            printer.print(type, tag, contents);
        }
    }

}
