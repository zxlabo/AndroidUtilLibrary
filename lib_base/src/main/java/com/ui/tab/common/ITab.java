package com.ui.tab.common;

import androidx.annotation.NonNull;
import androidx.annotation.Px;

/**
 * HiTab对外接口
 */
public interface ITab<D> extends ITabLayout.OnTabSelectedListener<D> {
    void setHiTabInfo(@NonNull D data);

    /**
     * 动态修改某个item的大小
     */
    void resetHeight(@Px int height);

}
