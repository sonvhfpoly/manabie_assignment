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
    fun getAllTodoTasks(): List<TodoTasks>

    @Insert
    fun insertTodoTask(todoTasks: TodoTasks): Long

    @Update
    fun updateTodoTask(todoTasks: TodoTasks): Int

    @Query("select * from todotasks where completed = 1")
    fun getCompletedTasks(): List<TodoTasks>

    @Query("select * from todotasks where completed = 0")
    fun getIncompleteTasks(): List<TodoTasks>
}