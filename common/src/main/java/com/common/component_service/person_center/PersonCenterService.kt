package com.common.component_service.person_center

import com.alibaba.android.arouter.facade.template.IProvider

interface PersonCenterService:IProvider {
    fun sayHello(name: String?): String
}

const val PERSON_CENTER_SERVICE_PATH = "/person_center/hello"
