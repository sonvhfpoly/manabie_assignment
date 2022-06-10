package com.manabie.sonvh.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.manabie.sonvh.model.dao.TodoTasksDao
import com.manabie.sonvh.model.entity.TodoTasks

@Database(entities = [TodoTasks::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoTasksDao(): TodoTasksDao

    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase{
            return INSTANCE ?: synchronized(this){
                val instance =  Room.databaseBuilder(context.applicationContext, TodoDatabase::class.java, "todotasks.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }

}