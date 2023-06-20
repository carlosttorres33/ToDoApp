package com.carlostorres.todoapp.addtask.data

import com.carlostorres.todoapp.addtask.ui.model.TaskModdel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    val tasks: Flow<List<TaskModdel>> =
        taskDao.getTask().map { items -> items.map { TaskModdel(it.id, it.task, it.selected) } }

    suspend fun add(taskModel: TaskModdel) {
        taskDao.addTask(taskModel.toData())
    }

    suspend fun update(taskModel: TaskModdel) {

        taskDao.updateTask(taskModel.toData())

    }

    suspend fun delete(taskModel: TaskModdel) {
        taskDao.deleteTask(taskModel.toData())

    }

}

fun TaskModdel.toData(): TaskEntity{
    return TaskEntity(this.id, this.task, this.selected)
}