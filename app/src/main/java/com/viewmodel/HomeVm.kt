package com.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.common.base.BaseViewModel

/**
 * author : Naruto
 * date   : 2021/9/28
 * desc   :
 */
class HomeVm:BaseViewModel() {
    protected val _coroutinesLiveData: MutableLiveData<String> = MutableLiveData()
    val coroutinesLiveData: LiveData<String> = _coroutinesLiveData
}