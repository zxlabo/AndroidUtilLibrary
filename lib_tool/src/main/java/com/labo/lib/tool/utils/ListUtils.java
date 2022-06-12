package com.labo.lib.tool.utils;

import java.util.List;

/**
 * getSize  数据源长度
 * isEmpty  数据源是否为空
 */
public class ListUtils {

    /**
     * 数据源长度
     */
    public static <V> int getSize(List<V> sourceList) {
        return sourceList == null ? 0 : sourceList.size();
    }

    /**
     * 数据源是否为空
     */
    public static <V> boolean isEmpty(List<V> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }

}
