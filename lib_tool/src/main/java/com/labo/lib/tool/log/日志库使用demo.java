package com.labo.lib.tool.log;

import java.util.ArrayList;

public class 日志库使用demo {
    public void demo() {
        //使用控制台打印
        HiLogManager.getInstance().addPrinter(HiConsolePrinter.getInstance(new HiLogConfig() {

        }));

        //使用视图打印
        HiLogManager.getInstance().addPrinter(HiConsolePrinter.getInstance(new HiLogConfig() {

        }));

        //使用文件打印
        ArrayList<String> list = new ArrayList<>();
        list.add("hello");
        HiLogManager.getInstance().addPrinter(HiFilePrinter.getInstance(list, new HiLogConfig() {

        }));

    }
}
