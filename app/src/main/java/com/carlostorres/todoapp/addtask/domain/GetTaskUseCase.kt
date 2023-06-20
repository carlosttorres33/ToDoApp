package com.carlostorres.todoapp.addtask.domain

import com.carlostorres.todoapp.addtask.data.TaskRepository
import com.carlostorres.todoapp.addtask.ui.model.TaskModdel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskUseCase@Inject constructor(private val taskRepository: TaskRepository) {
    operator fun invoke():Flow<List<TaskModdel>>{
        return taskRepository.tasks
    }
}