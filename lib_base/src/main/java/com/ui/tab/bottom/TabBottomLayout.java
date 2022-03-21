package com.ui.tab.bottom;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.ui.tab.common.ITabLayout;
import com.labo.utils.R;
import com.labo.utils.SizeUtils;
import com.labo.utils.ViewUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Tips:
 * 1. 透明度和底部透出，列表可渲染高度问题
 * 2. 中间高度超过，凸起布局
 */
public class TabBottomLayout extends FrameLayout implements ITabLayout<TabBottomView, TabBottomBean<?>> {
    private List<OnTabSelectedListener<TabBottomBean<?>>> tabSelectedChangeListeners = new ArrayList<>();
    private TabBottomBean<?> selectedInfo;
    private float bottomAlpha = 1f;
    //TabBottom高度
    private static float tabBottomHeight = 50;
    //TabBottom的头部线条高度
    private float bottomLineHeight = 0.5f;
    //TabBottom的头部线条颜色
    private String bottomLineColor = "#dfe0e1";
    private List<TabBottomBean<?>> infoList;

    public TabBottomLayout(@NonNull Context context) {
        this(context, null);
    }

    public TabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void inflateInfo(@NonNull List<TabBottomBean<?>> infoList) {
        if (infoList.isEmpty()) {
            return;
        }
        this.infoList = infoList;
        // 移除之前已经添加的View
        for (int i = getChildCount() - 1; i > 0; i--) {
            removeViewAt(i);
        }
        selectedInfo = null;
        addBackground();
        //清除之前添加的HiTabBottom listener，Tips：Java foreach remove问题
        Iterator<OnTabSelectedListener<TabBottomBean<?>>> iterator = tabSelectedChangeListeners.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof TabBottomView) {
                iterator.remove();
            }
        }
        int height = SizeUtils.dp2px(tabBottomHeight);
        FrameLayout ll = new FrameLayout(getContext());
        int width = SizeUtils.getDisplayWidthInPx(getContext()) / infoList.size();
        ll.setTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < infoList.size(); i++) {
            final TabBottomBean<?> info = infoList.get(i);
            //Tips：为何不用LinearLayout：当动态改变child大小后Gravity.BOTTOM会失效
            LayoutParams params = new LayoutParams(width, height);
            params.gravity = Gravity.BOTTOM;
            params.leftMargin = i * width;

            TabBottomView tabBottom = new TabBottomView(getContext());
            tabSelectedChangeListeners.add(tabBottom);
            tabBottom.setHiTabInfo(info);
            ll.addView(tabBottom, params);
            tabBottom.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelected(info);
                }
            });
        }
        LayoutParams flPrams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        flPrams.gravity = Gravity.BOTTOM;
        addBottomLine();
        addView(ll, flPrams);

        fixContentView();
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<TabBottomBean<?>> listener) {
        tabSelectedChangeListeners.add(listener);
    }

    public void setTabAlpha(float alpha) {
        this.bottomAlpha = alpha;
    }

    public void setTabHeight(float tabHeight) {
        this.tabBottomHeight = tabHeight;
    }

    public void setBottomLineHeight(float bottomLineHeight) {
        this.bottomLineHeight = bottomLineHeight;
    }

    public void setBottomLineColor(String bottomLineColor) {
        this.bottomLineColor = bottomLineColor;
    }

    @Nullable
    @Override
    public TabBottomView findTab(@NonNull TabBottomBean<?> info) {
        ViewGroup ll = findViewWithTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if (child instanceof TabBottomView) {
                TabBottomView tab = (TabBottomView) child;
                if (tab.getHiTabInfo() == info) {
                    return tab;
                }
            }
        }
        return null;
    }

    @Override
    public void defaultSelected(@NonNull TabBottomBean<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    private void onSelected(@NonNull TabBottomBean<?> nextInfo) {
        for (OnTabSelectedListener<TabBottomBean<?>> listener : tabSelectedChangeListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        }
        this.selectedInfo = nextInfo;
    }


    private void addBottomLine() {
        View bottomLine = new View(getContext());
        bottomLine.setBackgroundColor(Color.parseColor(bottomLineColor));

        LayoutParams bottomLineParams =
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(bottomLineHeight));
        bottomLineParams.gravity = Gravity.BOTTOM;
        bottomLineParams.bottomMargin = SizeUtils.dp2px(tabBottomHeight - bottomLineHeight);
        addView(bottomLine, bottomLineParams);
        bottomLine.setAlpha(bottomAlpha);
    }

    private void addBackground() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_white_bg, null);

        LayoutParams params =
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(tabBottomHeight));
        params.gravity = Gravity.BOTTOM;
        addView(view, params);
        view.setAlpha(bottomAlpha);
    }

    private static final String TAG_TAB_BOTTOM = "TAG_TAB_BOTTOM";

    /**
     * 修复内容区域的底部Padding
     */
    private void fixContentView() {
        if (!(getChildAt(0) instanceof ViewGroup)) {
            return;
        }
        ViewGroup rootView = (ViewGroup) getChildAt(0);
        ViewGroup targetView = ViewUtil.findTypeView(rootView, RecyclerView.class);
        if (targetView == null) {
            targetView = ViewUtil.findTypeView(rootView, ScrollView.class);
        }
        if (targetView == null) {
            targetView = ViewUtil.findTypeView(rootView, AbsListView.class);
        }
        if (targetView != null) {
            targetView.setPadding(0, 0, 0, SizeUtils.dp2px(tabBottomHeight));
            targetView.setClipToPadding(false);
        }
    }

    public static void clipBottomPadding(ViewGroup targetView) {
        if (targetView != null) {
            targetView.setPadding(0, 0, 0, SizeUtils.dp2px(tabBottomHeight));
            targetView.setClipToPadding(false);
        }
    }

    public void resizeHiTabBottomLayout() {
        int width = SizeUtils.getDisplayWidthInPx(getContext()) / infoList.size();
        ViewGroup frameLayout = (ViewGroup) getChildAt(getChildCount() - 1);
        int childCount = frameLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View button = frameLayout.getChildAt(i);
            LayoutParams params = (LayoutParams) button.getLayoutParams();
            params.width = width;
            params.leftMargin = i * width;
            button.setLayoutParams(params);
        }
    }
}
