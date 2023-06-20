package com.carlostorres.todoapp.addtask.domain

import com.carlostorres.todoapp.addtask.data.TaskRepository
import com.carlostorres.todoapp.addtask.ui.model.TaskModdel
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(taskModel: TaskModdel){
        taskRepository.delete(taskModel)
    }

}