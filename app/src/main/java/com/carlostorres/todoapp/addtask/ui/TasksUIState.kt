package com.carlostorres.todoapp.addtask.ui

import com.carlostorres.todoapp.addtask.ui.model.TaskModdel

sealed interface TasksUIState {
    object Loading: TasksUIState
    data class  Error(val throwable: Throwable):TasksUIState
    data class Success(val tasks:List<TaskModdel>): TasksUIState
}