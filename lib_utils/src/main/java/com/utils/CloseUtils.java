package com.utils;

import java.io.Closeable;

/**
 * detail: 关闭 ( IO 流 ) 工具类
 * closeIO          关闭 IO
 * closeIOQuietly   安静关闭 IO
 * flush            将缓冲区数据输出
 */
public final class CloseUtils {

    private CloseUtils() {
    }
    /**
     * 安静关闭 IO
     * @param closeables Closeable[]
     */
    public static void closeIOQuietly(final Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception ignore) {
                }
            }
        }
    }

}