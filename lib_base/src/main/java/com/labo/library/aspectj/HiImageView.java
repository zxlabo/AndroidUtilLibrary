package com.labo.library.aspectj;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * 打印图片加载的大小
 * 监听AppCompatImageView的setImageDrawable方法，然后在后面插入 post代码
 * @author zhouxin
 */
public class HiImageView extends AppCompatImageView {

    public HiImageView(@NonNull Context context) {
        super(context);
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);


        //宽高 可能还是0 view.post
        post(new RunnableImpl(this, drawable));

    }

    class RunnableImpl implements Runnable {
        android.view.View view;
        android.graphics.drawable.Drawable drawable;

        public RunnableImpl(View view, Drawable drawable) {
            this.view = view;
            this.drawable = drawable;
        }

        @Override
        public void run() {
            int width = view.getWidth();
            int height = view.getHeight();

            int drawableWidth = drawable.getIntrinsicWidth();
            int drawableHeight = drawable.getIntrinsicHeight();

            if (width > 0 && height > 0) {
                if (drawableWidth >= 2 * width && drawableHeight >= 2 * height) {
                    android.util.Log.e("LargeBitmapChecker", "bitmap:[" + drawableWidth + "," + drawableHeight + "],view:[" + width + "," + height + "],className:" + getContext().getClass().getSimpleName());
                }
            }
            android.util.Log.e("LargeBitmapChecker", "bitmap:[" + drawableWidth + "," + drawableHeight + "],view:[" + width + "," + height + "],className:" + getContext().getClass().getSimpleName());
        }
    }
}
