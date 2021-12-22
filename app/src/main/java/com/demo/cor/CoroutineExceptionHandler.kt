package com.demo.cor

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


fun CoroutineScope.safeLaunch(work: suspend CoroutineScope.() -> Unit, onError: ((Throwable) -> Unit)? = null, onCompleted: () -> Unit = {}) {
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        onError?.invoke(exception)
        onCompleted.invoke()
    }

    launch(coroutineExceptionHandler) {
        work.invoke(this)
        onCompleted.invoke()
    }

}


