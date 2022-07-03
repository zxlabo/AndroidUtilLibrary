package com.dance.person.center

import com.common.component_service.person_center.PersonCenterService2

class PersonCenterServiceImpl2 : PersonCenterService2 {
    override fun sayHello(name: String?): String {
        return "这是PersonCenterServiceImpl2"
    }
}