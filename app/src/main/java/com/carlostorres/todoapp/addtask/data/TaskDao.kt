package com.carlostorres.todoapp.addtask.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Delete
    suspend fun deleteTask(item: TaskEntity)

    @Update
    suspend fun updateTask(item: TaskEntity)

    @Query("SELECT * from TaskEntity")
    fun getTask(): Flow<List<TaskEntity>>

    @Insert
    suspend fun addTask(item: TaskEntity)

}