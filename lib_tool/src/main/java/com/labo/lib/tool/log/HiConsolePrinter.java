package com.labo.lib.tool.log;

import static com.labo.lib.tool.log.HiLogConfig.MAX_LEN;

import android.util.Log;
import androidx.annotation.NonNull;


public class HiConsolePrinter extends HiLogPrinter {

    private static  HiConsolePrinter instance;

    private HiConsolePrinter(HiLogConfig config) {
        super(config);
    }

    public static HiConsolePrinter getInstance(@NonNull HiLogConfig config){
        if (instance==null){
            synchronized (HiConsolePrinter.class){
                if (instance==null){
                    instance = new HiConsolePrinter(config);
                }
            }
        }
        return instance;
    }

    @Override
    protected String formatMsg(@NonNull Object[] contents) {
        return LogMsgUtil.getCommonMsg(config, contents);
    }

    @Override
    void print(int level, String tag, @NonNull String printString) {
        int len = printString.length();
        int countOfSub = len / MAX_LEN;
        if (countOfSub > 0) {
            StringBuilder log = new StringBuilder();
            int index = 0;
            for (int i = 0; i < countOfSub; i++) {
                log.append(printString.substring(index, index + MAX_LEN));
                index += MAX_LEN;
            }
            if (index != len) {
                log.append(printString.substring(index, len));
            }
            Log.println(level, tag, log.toString());
        } else {
            Log.println(level, tag, printString);
        }
    }

}
