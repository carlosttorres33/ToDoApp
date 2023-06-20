package com.carlostorres.todoapp.addtask.ui.model

data class TaskModdel(
    val id: Int = System.currentTimeMillis().hashCode(),
    val task: String,
    var selected: Boolean = false
)
