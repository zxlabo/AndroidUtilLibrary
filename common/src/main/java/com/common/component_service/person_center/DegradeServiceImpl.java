package com.common.component_service.person_center;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.DegradeService;

/**
 * @author zhouxin
 * 实现DegradeService接口，并加上一个Path内容任意的注解即可
 */
@Route(path = "/route/DegradeService")
public class DegradeServiceImpl implements DegradeService {
    @Override
    public void onLost(Context context, Postcard postcard) {
        // do something.跳转到统一的错误页面
    }

    @Override
    public void init(Context context) {

    }
}