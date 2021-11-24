package com.demo.cor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoroutineVm : ViewModel() {

    private  fun request1() {
        viewModelScope.launch(Dispatchers.Main) {
            val msg="hello"
            printLog("aaa")
        }
    }


    private fun printLog(msg: String) {
        Log.e("测试：", msg)
    }
}