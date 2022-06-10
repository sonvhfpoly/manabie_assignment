package com.manabie.sonvh.domain

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.manabie.sonvh.model.TodoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val todoDao = TodoDatabase.getDatabase(application.applicationContext).todoTasksDao()

    fun sendEvent(event: Event) {
        when (event) {
            is InsertEvent -> {
                insertTodoTask(event)
            }
            is UpdateEvent -> {

            }
            is GetTasksEvent -> {

            }
            else -> {

            }
        }
    }

    private fun insertTodoTask(event: InsertEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.insertTodoTask(event.todoTasks)
        }
    }

    private fun handleGetTasks(event: GetTasksEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            when (event.taskType) {
                TaskType.ALL -> {
                    todoDao.getAllTodoTasks()
                }
                TaskType.COMPLETED -> {
                    todoDao.getCompletedTasks()
                }
                TaskType.INCOMPLETE -> {
                    todoDao.getIncompleteTasks()
                }
            }
        }
    }
}