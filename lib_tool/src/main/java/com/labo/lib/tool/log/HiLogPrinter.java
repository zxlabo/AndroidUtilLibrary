package com.labo.lib.tool.log;

import androidx.annotation.NonNull;

public abstract class HiLogPrinter {

    protected HiLogConfig config;

    public HiLogPrinter(HiLogConfig config) {
        this.config = config;
    }

    void print(int level, String tag, @NonNull Object[] contents) {
        if (config == null || !config.enable()) {
            return;
        }
        print(level, tag, formatMsg(contents));
    }

    /**
     * 对打印的数据进行format自定义
     */
    protected abstract String formatMsg(Object[] contents);

    /**
     * 打印数据
     */
    abstract void print(int level, String tag, @NonNull String contents);


}
