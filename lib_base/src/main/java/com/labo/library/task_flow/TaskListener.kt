package com.labo.library.task_flow

interface TaskListener {
    fun onStart(task: Task)

    fun onRunning(task: Task)

    fun onFinish(task: Task)

}