package com.demo.coroutine_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.demo.work.UploadWorker
import com.library.R
import kotlinx.android.synthetic.main.activity_corountine.*
import kotlinx.coroutines.*
import kotlin.coroutines.Continuation

class CoroutineActivity : AppCompatActivity() {

    var time = 0L
    var job: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corountine)
        time = System.currentTimeMillis()

        btn_1.setOnClickListener {
            startScene1()
        }
        btn_2.setOnClickListener {
            startScene2()
        }
        btn_3.setOnClickListener {
            val callback = Continuation<String>(Dispatchers.Main) { result ->
                Log.e("MainActivity", result.getOrNull() ?: "")
            }
            CoroutineScene2_decompiled.request1(callback)
        }
        btn_4.setOnClickListener {

            //自定义挂起函数
            //虽然这是耗时函数，但是它实现了挂起，并不会阻塞主线程
            lifecycleScope.launch {
                val result = CoroutineScene3.parseAssetsFile(assets, "demo.txt")
                printLog("Thread:${Thread.currentThread().name},result：${result}")

            }
        }
        btn_5.setOnClickListener {
            //执行耗时任务
            lifecycleScope.launch {
                //执行耗时任务，它并没有实现挂起，只是在子线程去执行了任务
                val msg = withContext(Dispatchers.Default) {
                    Thread.sleep(20000)
                    "hello"
                }
                printLog("Thread:${Thread.currentThread().name},result：${msg}")
                //打印：Thread:main,result：hello
            }
        }
        btn_6.setOnClickListener {

            val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
            scope.safeLaunch(work = {
                printLog("work")
                val result = CoroutineScene3.getResult()
                printLog(result)
            }, onCompleted = {
                printLog("onCompleted")
            }, onError = {
                printLog("onError:${it.message}")
            })
        }

        btn_7.setOnClickListener {
            val uploadWorkRequest: WorkRequest =
                OneTimeWorkRequestBuilder<UploadWorker>()
                    .build()

            WorkManager
                .getInstance(this)
                .enqueue(uploadWorkRequest)
        }
        btn_8.setOnClickListener {
            testCancel()

        }


    }

    /**
     * 测试协程取消
     * 父协程的取消，同时会取消子协程，但不会影响兄弟协程和它的父协程。
     */

    private fun testCancel() {
        var i = 0
        var j = 0
        var job2: Job?=null
        runBlocking {
          val job1=  launch(Dispatchers.Default+CoroutineName("1111")) {
                job2 = launch(Dispatchers.Default+CoroutineName("2222")) {
                    var nextTime2 = System.currentTimeMillis()
                    while (j < 5) {
                        if (System.currentTimeMillis() > nextTime2) {
                            printLog("执行child----${j}${isActive}${this.coroutineContext.get(CoroutineName)}")
                            j++
                            nextTime2 += 500
                        }
                    }
                }

                var nextTime = System.currentTimeMillis()
                while (i < 5) {
                    if (System.currentTimeMillis() > nextTime) {
                        printLog("执行f----${i}${isActive}${this.coroutineContext.get(CoroutineName)}")
                        i++
                        nextTime += 500
                    }
                }

            }
            delay(1000)
            printLog("取消协程")
            job1.cancel()
//            job2.cancel()

        }
    }


    private fun startScene1() {
        lifecycleScope.launch {
            val result1 = request1()
            val result2 = request2(result1)
            val result3 = request3(result2)
            updateUi(result3)
        }
    }

    private suspend fun request1(): String {
        // delay不会暂停线程，但会暂停所在的协程，将协程挂起
        delay(2 * 1000)
        //Thread.sleep(2*1000) 让线程休眠
        printLog("request1 工作所在的线程：${Thread.currentThread().name}")
        return "request1 的结果"
    }

    private suspend fun request4() {
        lifecycleScope.async {
            // delay不会暂停线程，但会暂停所在的协程，将协程挂起
            delay(2 * 1000)
            //Thread.sleep(2*1000) 让线程休眠
            printLog("request4 工作所在的线程：${Thread.currentThread().name}")

        }

    }

    private fun startScene2() {
        lifecycleScope.launch {
            val result1 = request1()
            withContext(Dispatchers.IO) {
                request4()
            }
            val deferred2 = async { request2(result1) }
            val deferred3 = async { request3(result1) }
            printLog("11111")
            updateUi(deferred2.await() + deferred3.await())
            printLog("2222")
        }
    }


    private fun updateUi(result3: String) {
        printLog("updateUi 工作所在的线程：${Thread.currentThread().name}")
        printLog("updateUi ${result3}")
    }


    suspend fun request2(msg: String): String {
        printLog("request2 执行")
        delay(2 * 1000)
        printLog("request2 工作所在的线程：${Thread.currentThread().name}")
        return "${msg}request2 的结果"
    }

    suspend fun request3(msg: String): String {
        delay(3 * 1000)
        printLog("request3 工作所在的线程：${Thread.currentThread().name}")
        return "${msg}request3 的结果"
    }

    fun printLog(msg: String) {
        Log.e("测试：${System.currentTimeMillis() - time}", msg)
    }
}