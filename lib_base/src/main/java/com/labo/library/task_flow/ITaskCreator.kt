package com.labo.library.task_flow

interface ITaskCreator {
    fun createTask(taskName: String): Task
}