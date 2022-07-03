package com.common.base;

/**
 * @author zhouxin
 * 接口：可以做组件中服务的注册或卸载
 */
public interface IComponentService {

    void onRegister();

    void unRegister();

}
