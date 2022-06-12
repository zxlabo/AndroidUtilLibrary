package com.labo.lib.tool.utils.record;

import java.util.HashMap;

/**
 * 采用单例管理各个耗时统计的数据。
 */
public class TimeMonitorManager {

    private static TimeMonitorManager mTimeMonitorManager = null;

    private final HashMap<Integer, TimeMonitor> mTimeMonitorMap = new HashMap<>();

    private TimeMonitorManager() {

    }

    public static synchronized TimeMonitorManager getInstance() {
        if (mTimeMonitorManager == null) {
            mTimeMonitorManager = new TimeMonitorManager();
        }
        return mTimeMonitorManager;
    }

    /**
     * 初始化打点模块
     */
    public void initTimeMonitor(int id) {
        if (mTimeMonitorMap.get(id) != null) {
            mTimeMonitorMap.remove(id);
        }
        getTimeMonitor(id).startMonitor();
    }

    /**
     * 获取打点器
     */
    public TimeMonitor getTimeMonitor(int id) {
        TimeMonitor monitor = mTimeMonitorMap.get(id);
        if (monitor == null) {
            monitor = new TimeMonitor(id);
            mTimeMonitorMap.put(id, monitor);
        }
        return monitor;
    }

    /**
     * 获取应用启动耗时打点器
     */
    public TimeMonitor getAppStartTimeMonitor() {
        return getTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_APPLICATION_START);
    }

}
