package com.carlostorres.todoapp.addtask.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlostorres.todoapp.addtask.domain.AddTaskUseCase
import com.carlostorres.todoapp.addtask.domain.DeleteTaskUseCase
import com.carlostorres.todoapp.addtask.domain.GetTaskUseCase
import com.carlostorres.todoapp.addtask.domain.UpdateTaskUseCase
import com.carlostorres.todoapp.addtask.ui.TasksUIState.*
import com.carlostorres.todoapp.addtask.ui.model.TaskModdel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    getTaskUseCase: GetTaskUseCase,
): ViewModel() {

    val uiState: StateFlow<TasksUIState> = getTaskUseCase().map(::Success)
        .catch { Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)


    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog : LiveData<Boolean> = _showDialog

    fun dialogClose() {
        _showDialog.value = false
    }

    fun onTaskCreated(task: String) {
        _showDialog.value = false

        viewModelScope.launch {
            addTaskUseCase(TaskModdel(task=task))
        }
    }

    fun onShowDialogClick() {
        _showDialog.value = true
    }

    fun onCheckBoxSelected(taskModel: TaskModdel) {

//        ACTUALIZAR CHECK
//        val index = _tasks.indexOf(taskModel)
//        _tasks[index] = _tasks[index].let {
//            it.copy(selected = !it.selected)
//        }
        viewModelScope.launch {
            updateTaskUseCase(
                taskModel.copy(selected = !taskModel.selected)
            )
        }
    }

    fun onItemRemove(taskModel: TaskModdel) {
//        BORRAR ITEM
//        val task = _tasks.find { it.id == taskModel.id }
//        _tasks.remove(task)
        viewModelScope.launch {
            deleteTaskUseCase(taskModel)
        }
    }

}