package com.common.utils;

import java.util.HashMap;


/**
 * @author zhouxin
 * desc：封装了serviceMap，key是服务的name，value是服务的实例。
 */
public class RouterServiceManager {

    private HashMap<String, Object> serviceMap = new HashMap<>();

    private static volatile RouterServiceManager instance;

    private RouterServiceManager() {
    }

    public static RouterServiceManager getInstance() {
        if (instance == null) {
            synchronized (RouterServiceManager.class) {
                if (instance == null) {
                    instance = new RouterServiceManager();
                }
            }
        }
        return instance;
    }


    public synchronized void addService(String serviceName, Object serviceImpl) {
        if (serviceName == null || serviceImpl == null) {
            return;
        }
        serviceMap.put(serviceName, serviceImpl);
    }

    public synchronized Object getService(String serviceName) {
        if (serviceName == null) {
            return null;
        }
        return serviceMap.get(serviceName);
    }

    public synchronized void removeService(String serviceName) {
        if (serviceName == null) {
            return;
        }
        serviceMap.remove(serviceName);
    }


}
