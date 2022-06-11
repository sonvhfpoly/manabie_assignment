package com.manabie.sonvh.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.manabie.sonvh.model.entity.TodoTasks
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoTasksDao {

    @Query("select * from todotasks")
    fun getAllTodoTasks(): Flow<List<TodoTasks>>

    @Insert
    fun insertTodoTask(todoTasks: TodoTasks): Long

    @Query("update todotasks set completed = :isCompleted where uid = :taskId")
    fun updateTodoTask(taskId: Int, isCompleted: Boolean): Int

    @Query("select * from todotasks where completed = 1")
    fun getCompletedTasks(): Flow<List<TodoTasks>>

    @Query("select * from todotasks where completed = 0")
    fun getIncompleteTasks(): Flow<List<TodoTasks>>
}