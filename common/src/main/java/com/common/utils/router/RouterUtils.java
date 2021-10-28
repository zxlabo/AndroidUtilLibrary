package com.common.utils.router;

import android.net.Uri;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author zhouxin
 */
public class RouterUtils {

    private RouterUtils() {
        throw new AssertionError();
    }

    public static void navigation(String path) {
        ARouter.getInstance().build(path).navigation();
    }

    public static void navigation(Uri url) {
        ARouter.getInstance().build(url).navigation();
    }

    public static Postcard getPostcard(String path) {
        return ARouter.getInstance().build(path);
    }


    public static Postcard getPostcard(Uri uri) {
        return ARouter.getInstance().build(uri);
    }


}
