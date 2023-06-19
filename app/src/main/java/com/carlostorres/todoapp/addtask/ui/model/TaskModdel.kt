package com.carlostorres.todoapp.addtask.ui.model

import java.util.UUID

data class TaskModdel(
    val id: Long = System.currentTimeMillis(),
    val task: String,
    var selected: Boolean = false
)
