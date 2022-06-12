package com.labo.lib.tool.log;

public interface HiLogFormatter<T> {

    String format(T data);
}