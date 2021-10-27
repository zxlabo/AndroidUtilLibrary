package com.viewmodel

import androidx.lifecycle.MutableLiveData
import com.common.base.BaseViewModel

/**
 * author : Naruto
 * date   : 2021/9/28
 * desc   :
 */
class HomeVm:BaseViewModel() {
    val info=MutableLiveData<String>()
}