package com.ui.tab.bottom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;

import com.ui.tab.common.ITab;
import com.utils.R;

public class TabBottomView extends RelativeLayout implements ITab<TabBottomBean<?>> {
    private TabBottomBean<?> tabInfo;
    private ImageView tabImageView;
    private TextView tabIconView;
    private TextView tabNameView;

    public TabBottomView(Context context) {
        this(context, null);
    }

    public TabBottomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_tab_bottom, this);
        tabImageView = findViewById(R.id.iv_image);
        tabIconView = findViewById(R.id.tv_icon);
        tabNameView = findViewById(R.id.tv_name);
    }

    @Override
    public void setHiTabInfo(@NonNull TabBottomBean hiTabBottomInfo) {
        this.tabInfo = hiTabBottomInfo;
        inflateInfo(false, true);
    }

    public TabBottomBean getHiTabInfo() {
        return tabInfo;
    }

    public ImageView getTabImageView() {
        return tabImageView;
    }

    public TextView getTabIconView() {
        return tabIconView;
    }

    public TextView getTabNameView() {
        return tabNameView;
    }

    /**
     * 改变某个tab的高度
     *
     * @param height
     */
    @Override
    public void resetHeight(@Px int height) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = height;
        setLayoutParams(layoutParams);
        getTabNameView().setVisibility(View.GONE);
    }

    private void inflateInfo(boolean selected, boolean init) {
        if (tabInfo.tabType == TabBottomBean.TabType.ICON) {
            if (init) {
                tabImageView.setVisibility(GONE);
                tabIconView.setVisibility(VISIBLE);
                Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), tabInfo.iconFont);
                tabIconView.setTypeface(typeface);
                if (!TextUtils.isEmpty(tabInfo.name)) {
                    tabNameView.setText(tabInfo.name);
                }
            }

            if (selected) {
                tabIconView.setText(TextUtils.isEmpty(tabInfo.selectedIconName) ? tabInfo.defaultIconName : tabInfo.selectedIconName);
                tabIconView.setTextColor(getTextColor(tabInfo.tintColor));
                tabNameView.setTextColor(getTextColor(tabInfo.tintColor));
            } else {
                tabIconView.setText(tabInfo.defaultIconName);
                tabIconView.setTextColor(getTextColor(tabInfo.defaultColor));
                tabNameView.setTextColor(getTextColor(tabInfo.defaultColor));
            }

        } else if (tabInfo.tabType == TabBottomBean.TabType.BITMAP) {
            if (init) {
                tabImageView.setVisibility(VISIBLE);
                tabIconView.setVisibility(GONE);
                if (!TextUtils.isEmpty(tabInfo.name)) {
                    tabNameView.setText(tabInfo.name);
                }
            }
            if (selected) {
                tabImageView.setImageBitmap(tabInfo.selectedBitmap);
            } else {
                tabImageView.setImageBitmap(tabInfo.defaultBitmap);
            }
        }
    }

    @Override
    public void onTabSelectedChange(int index, @Nullable TabBottomBean<?> prevInfo, @NonNull TabBottomBean<?> nextInfo) {
        if (prevInfo != tabInfo && nextInfo != tabInfo || prevInfo == nextInfo) {
            return;
        }
        if (prevInfo == tabInfo) {
            inflateInfo(false, false);
        } else {
            inflateInfo(true, false);
        }
    }

    @ColorInt
    private int getTextColor(Object color) {
        if (color instanceof String) {
            return Color.parseColor((String) color);
        } else {
            return (int) color;
        }
    }
}
