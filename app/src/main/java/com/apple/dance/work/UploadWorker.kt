package com.apple.dance.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.apple.dance.coroutine_demo.printLog
import com.apple.dance.coroutine_demo.safeLaunch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class UploadWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {

        val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        scope.safeLaunch(work = {
            printLog("执行任务")
        }
        )
        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }
}