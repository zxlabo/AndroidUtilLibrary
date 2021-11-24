package com.demo.cor

import android.content.res.AssetManager
import android.util.Log
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder

/**
 * 演示以异步的方式 读取 asstes目录下的文件,并且适配协程的写法，让他真正的挂起函数
 *
 * 方便调用方 直接以同步的形式拿到返回值
 */
fun printLog(msg: String) {
    Log.e("打印信息", msg)
}

object CoroutineScene3 {

    suspend fun getResult(): String {
        return suspendCancellableCoroutine { continuation ->
            /**
             * 耗时任务，获取result
             * 开启子线程，当执行完毕，调用 continuation.resumeWith 方法，将结果回调出去
             */
            try {
                Thread {
                    //模拟耗时操作
                    Thread.sleep(2000)
                    val successMsg = "result success"
                    printLog("协程恢复,将接口回调")
                    continuation.resumeWith(result = Result.success(successMsg))
                }.start()
            } catch (e: Exception) {
                printLog("发生异常：${e.message}，将异常信息回调向上传递")
                continuation.resumeWith(result = Result.failure(e))
            }
            /**
             * 协程取消，处理清除工作
             */
            continuation.invokeOnCancellation {
                //处理清除工作
                printLog("协程取消")
            }
        }
    }

    suspend fun parseAssetsFile(assetManager: AssetManager, fileName: String): String {
        return suspendCancellableCoroutine { continuation ->
            Thread(Runnable {
                val inputStream = assetManager.open(fileName)
                val br = BufferedReader(InputStreamReader(inputStream))
                var line: String?
                var stringBuilder = StringBuilder()
                do {
                    line = br.readLine()
                    if (line != null) stringBuilder.append(line) else break
                } while (true)

                inputStream.close()
                br.close()

                Thread.sleep(2000)

                Log.e("coroutine", "parseassetsfile completed")
                continuation.resumeWith(Result.success(stringBuilder.toString()))
            }).start()
        }
    }
}