package com.labo.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.widget.FrameLayout;

/**
 * @author zhouxin
 * 当使用沉浸式状态栏的时候，使用ScrollView+adjustResize,键盘会遮挡住输入框
 * 解决键盘档住输入框
 * (1) 找到Activity的最外层布局控件，我们知道所有的Activity都是DecorView，它就是一个FrameLayout控件，该控件id是系统写死叫R.id.content，
 * 就是我们setContentView时，把相应的View放在此FrameLayout控件里。所以content.getChildAt(0)获取到的mChildOfContent，
 * 也就是我们用setContentView放进去的View。
 * (2) 给我们的Activity的xml布局View设置一个Listener监听
 * View.getViewTreeObserver()可以获取一个ViewTreeObserver对象——它是一个观察者，用以监听当前View树所发生的变化。
 * 这里所注册的addOnGlobalLayoutListener，就是会在当前的View树的全局布局（GlobalLayout）发生变化、或者其中的View可视状态有变化时，进行通知回调。
 * 『软键盘弹出/隐 』都能监听到。
 * (3) 获取当前界面可用高度
 * (4) 重设高度， 我们计算出的可用高度，是目前在视觉效果上能看到的界面高度。但当前界面的实际高度是比可用高度要多出一个软键盘的距离的。
 */
public class SoftHideKeyBoardUtil {

    public static void assistActivity(Activity activity) {
        new SoftHideKeyBoardUtil(activity);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;
    //为适应华为小米等手机键盘上方出现黑条或不适配
    private FrameLayout.LayoutParams frameLayoutParams;
    //获取setContentView本来view的高度
    private int contentHeight;
    //只用获取一次
    private boolean isfirst = true;
    //状态栏高度
    private int statusBarHeight;
    private SoftHideKeyBoardUtil(Activity activity) {
        //1､找到Activity的最外层布局控件，它其实是一个DecorView,它所用的控件就是FrameLayout
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        //2､获取到setContentView放进去的View
        mChildOfContent = content.getChildAt(0);
        //3､给Activity的xml布局设置View树监听，当布局有变化，如键盘弹出或收起时，都会回调此监听
        //4､软键盘弹起会使GlobalLayout发生变化
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            if (isfirst) {
                contentHeight = mChildOfContent.getHeight();//兼容华为等机型
                isfirst = false;
            }
            //5､当前布局发生变化时，对Activity的xml布局进行重绘
            possiblyResizeChildOfContent();
        });
        //6､获取到Activity的xml布局的放置参数
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    /**
     * 获取界面可用高度，如果软键盘弹起后，Activity的xml布局可用高度需要减去键盘高度
     */
    private void possiblyResizeChildOfContent() {
        //1､获取当前界面可用高度，键盘弹起后，当前界面可用布局会减少键盘的高度
        int usableHeightNow = computeUsableHeight();
        //2､如果当前可用高度和原始值不一样
        if (usableHeightNow != usableHeightPrevious) {
            //3､获取Activity中xml中布局在当前界面显示的高度
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            //4､Activity中xml布局的高度-当前可用高度
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            //5､高度差大于屏幕1/4时，说明键盘弹出
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // 6､键盘弹出了，Activity的xml布局高度应当减去键盘高度
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    frameLayoutParams.height = usableHeightSansKeyboard - heightDifference + statusBarHeight;
                } else {
                    frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
                }
            } else {
                frameLayoutParams.height = contentHeight;
            }
            //7､ 重绘Activity的xml布局
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        // 全屏模式下：直接返回r.bottom，r.top其实是状态栏的高度
        return (r.bottom - r.top);
    }

}
