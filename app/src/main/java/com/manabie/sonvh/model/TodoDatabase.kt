package com.manabie.sonvh.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.manabie.sonvh.model.dao.TodoTasksDao
import com.manabie.sonvh.model.entity.TodoTasks

@Database(entities = [TodoTasks::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoTasksDao(): TodoTasksDao

}