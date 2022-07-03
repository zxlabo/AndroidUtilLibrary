package com.dance.person.center

import com.common.base.BaseModuleApplication
import com.common.component_service.person_center.PERSON_CENTER_SERVICE_PATH
import com.common.utils.RouterServiceManager

class PersonCenterApplication : BaseModuleApplication() {


    override fun initModuleApplication() {
        RouterServiceManager.getInstance().addService(PERSON_CENTER_SERVICE_PATH, PersonCenterServiceImpl2())
    }

    override fun initModuleData() {

    }

}