package com.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AgentWebView extends WebView {
    public AgentWebView(@NonNull Context context) {
        super(context);
    }

    public AgentWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AgentWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AgentWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public AgentWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }

    @Override
    public void setOverScrollMode(int mode) {
        try {
            super.setOverScrollMode(mode);
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

}
