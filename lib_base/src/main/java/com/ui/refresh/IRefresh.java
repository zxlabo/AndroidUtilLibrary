package com.ui.refresh;

public interface IRefresh {
    /**
     * 刷新时是否禁止滚动
     *
     * @param disableRefreshScroll 否禁止滚动
     */
    void setDisableRefreshScroll(boolean disableRefreshScroll);

    /**
     * 刷新完成
     */
    void refreshFinished();

    /**
     * 设置下拉刷新的监听器
     *
     * @param iRefreshListener 刷新的监听器
     */
    void setRefreshListener(IRefreshListener iRefreshListener);

    /**
     * 设置下拉刷新的视图
     *
     * @param absOverView 下拉刷新的视图
     */
    void setRefreshOverView(AbsOverView absOverView);


    interface IRefreshListener {

        void onRefresh();

        boolean enableRefresh();
    }

}
