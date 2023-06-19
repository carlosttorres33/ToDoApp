package com.carlostorres.todoapp.addtask.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.carlostorres.todoapp.addtask.ui.model.TaskModdel
import javax.inject.Inject

class TaskViewModel @Inject constructor(): ViewModel() {

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog : LiveData<Boolean> = _showDialog

    private val _tasks = mutableStateListOf<TaskModdel>()
    val tasks : List<TaskModdel> = _tasks

    fun dialogClose() {
        _showDialog.value = false
    }

    fun onTaskCreated(task: String) {
        _showDialog.value = false
        _tasks.add(TaskModdel(task = task))
        Log.d("Carlos", task)
    }

    fun onShowDialogClick() {
        _showDialog.value = true
    }

    fun onCheckBoxSelected(taskModel: TaskModdel) {

        val index = _tasks.indexOf(taskModel)
        _tasks[index] = _tasks[index].let {
            it.copy(selected = !it.selected)
        }

    }

    fun onItemRemove(taskModel: TaskModdel) {
        val task = _tasks.find { it.id == taskModel.id }
        _tasks.remove(task)
    }


}