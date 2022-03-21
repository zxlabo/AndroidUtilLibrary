package com.labo.utils;

import android.content.Context;
import android.location.LocationManager;

/**
 * author : Naruto
 * date   : 2021/6/4
 * desc   :
 * isLocServiceEnable   手机是否开启位置服务
 */
public class LocationUtils {
    /**
     * 手机是否开启位置服务，如果没有开启那么所有app将不能使用定位功能
     */
    public static boolean isLocServiceEnable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) return false;
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }
}
