package com.library.log;

public interface HiLogFormatter<T> {

    String format(T data);
}