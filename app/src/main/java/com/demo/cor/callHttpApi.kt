//package com.demo.cor
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.*
//import kotlin.coroutines.AbstractCoroutineContextElement
//import kotlin.coroutines.CoroutineContext
//
//
///**
// * 在ViewModel中调用 后端 HTTP API 接口
// *
// * @param doOnCompleted 请求完成之后的回调（包括成功和失败）
// * @param work 调用后端接口的任务代码块
// * @param failed 捕获接口返回的错误信息，具体看这段 [描述][HttpApiFailedHandler.invokeOnFailed]
// */
//fun ViewModel.callHttpApi(
//    failed: CoroutineContext.(HttpApiFailure) -> Unit = {},
//    doOnCompleted: () -> Unit = {},
//    work: suspend CoroutineScope.() -> Unit
//) = viewModelScope.launch(
//    HttpApiFailedHandler(doOnCompleted, failed)
//) {
//    work.invoke(this)
//    doOnCompleted.invoke()
//}
//
//
///**
// * 在协程作用域中调用 后端 HTTP API 接口
// *
// * @param start 参考 [CoroutineScope.launch] 的 start 参数
// * @param doOnCompleted 请求完成之后的回调（包括成功和失败）
// * @param work 调用后端接口的任务代码块
// * @param failed 捕获接口返回的错误信息，具体看这段 [描述][HttpApiFailedHandler.invokeOnFailed]
// */
//fun CoroutineScope.callHttpApi(
//    start: CoroutineStart = CoroutineStart.DEFAULT,
//    failed: CoroutineContext.(HttpApiFailure) -> Unit = {},
//    doOnCompleted: () -> Unit = {},
//    work: suspend CoroutineScope.() -> Unit
//) = launch(HttpApiFailedHandler(doOnCompleted, failed), start) {
//    work.invoke(this)
//    doOnCompleted.invoke()
//}
//
///**
// * @description 后端 API 接口请求失败的通用处理器
// *
// * @param invokeOnFailed 当捕获到后端接口返回错误，会首先调用此函数以通知调用方，调用方通过接收到的
// *                       [HttpApiFailure] 参数可以优先进行一些处理，然后 [此类][HttpApiFailedHandler]
// *                       会根据情况进行 toast 提示错误信息，和强制拦截
// *
// * @auther: yaoyakun
// * @create 6/21/21 7:34 PM
// */
//class HttpApiFailedHandler(
//    private val doOnCompleted: () -> Unit = {},
//    private val invokeOnFailed: CoroutineContext.(HttpApiFailure) -> Unit = {}
//) :
//    AbstractCoroutineContextElement(
//        CoroutineExceptionHandler
//    ), CoroutineExceptionHandler {
//
//    private fun toast(fail: HttpApiFailure) {
//        val result = fail.message
//        if (fail.shouldToast) result?.let { ToastUtils.show(it) }
//    }
//
//    override fun handleException(context: CoroutineContext, exception: Throwable) {
//        if (exception !is ApiResultException) {
//            LogUploadUtil.writeLog(LogUploadUtil.getStackTrace(exception))
//        }
//        val simpleTrMessage = "${exception.javaClass.simpleName}:${exception.localizedMessage}"
//        LogUtils.e("${Thread.currentThread()} throw $simpleTrMessage")
//        val apiFailure = HttpApiFailure(exception).also {
//            context.invokeOnFailed(it)
//            doOnCompleted.invoke()
//        }
//
//
//    }
//
//}
//
//
//data class HttpApiFailure(
//    val code: String,
//    val message: String?,
//    val originException: Throwable
//) {
//    constructor(tr: Throwable) : this(
//        if (tr is ApiResultException) tr.code else ApiResultException.UNKNOWN_ERROR,
//        parseThrowable(tr),
//        tr
//    )
//
//    internal var shouldToast = true
//        private set
//
//    /**
//     * 不要 toast 提示 message
//     */
//    fun dontToast() {
//        shouldToast = false
//    }
//
//    companion object {
//        private fun parseThrowable(tr: Throwable): String? = ExceptionParser.parser(tr)
//    }
//}