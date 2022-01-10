package com.utils;

import android.content.Context;
import android.net.wifi.WifiManager;

/**
 * @author zhouxin
 */
public class WifiUtils {
    /**
     * 检查wifi是否可用
     * 注意权限：ACCESS_WIFI_STATE
     */
    public static boolean checkWifiIsEnable(){
        WifiManager wifiManager = (WifiManager) AppGlobals.INSTANCE.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return null != wifiManager && wifiManager.isWifiEnabled();
    }

}
