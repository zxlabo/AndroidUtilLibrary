package com.dance.person.center;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.common.component_service.person_center.PersonCenterService;
import com.common.component_service.person_center.PersonCenterServiceKt;

/**
 * @author zhouxin
 * desc：实现接口
 */
@Route(path = PersonCenterServiceKt.PERSON_CENTER_SERVICE_PATH, name = "测试服务")
public class PersonCenterServiceImpl implements PersonCenterService {

    @Override
    public void init(Context context) {

    }

    @Override
    public String sayHello(String name) {
        return "hello, 这是个人中心在呼叫" + name;
    }


}