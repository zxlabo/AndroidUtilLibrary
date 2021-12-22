package com.ui.list.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.utils.SizeUtils;

/**
 * 下拉刷新的Overlay视图,可以重载这个类来定义自己的Overlay
 */
public abstract class AbsOverView extends FrameLayout {

    protected HiRefreshState mState = HiRefreshState.STATE_INIT;
    /**
     * 触发下拉刷新 需要的最小高度
     */
    public int mPullRefreshHeight;
    /**
     * 最小阻尼
     */
    public float minDamp = 1.6f;
    /**
     * 最大阻尼
     */
    public float maxDamp = 2.2f;

    public AbsOverView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        preInit();
    }

    public AbsOverView(Context context, AttributeSet attrs) {
        super(context, attrs);
        preInit();
    }

    public AbsOverView(Context context) {
        super(context);
        preInit();
    }

    protected void preInit() {
        mPullRefreshHeight = SizeUtils.dp2px(66);
        init();
    }

    /**
     * 初始化
     */
    public abstract void init();

    protected abstract void onScroll(int scrollY, int pullRefreshHeight);

    /**
     * 显示Overlay
     */
    protected abstract void onVisible();

    /**
     * 超过Overlay，释放就会加载
     */
    public abstract void onOver();

    /**
     * 开始加载
     */
    public abstract void onRefresh();

    /**
     * 加载完成
     */
    public abstract void onFinish();

    /**
     * 设置状态
     *
     * @param state 状态
     */
    public void setState(HiRefreshState state) {
        mState = state;
    }

    /**
     * 获取状态
     *
     * @return 状态
     */
    public HiRefreshState getState() {
        return mState;
    }

}