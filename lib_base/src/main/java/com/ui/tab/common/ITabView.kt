package com.ui.tab.common

import androidx.annotation.Px

/**
 * HiTab对外接口
 */
interface ITabView<D> : ITabLayout.OnTabSelectedListener<D> {
    fun setHiTabInfo(data: D)

    /**
     * 动态修改某个item的大小
     */
    fun resetHeight(@Px height: Int)
}