package com.manabie.sonvh.domain

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.manabie.sonvh.model.TodoDatabase
import com.manabie.sonvh.model.entity.TodoTasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val todoDao = TodoDatabase.getDatabase(application.applicationContext).todoTasksDao()

    private val allTasks by lazy {
        todoDao.getAllTodoTasks().asLiveData()
    }
    private val incompleteTasks by lazy {
        todoDao.getIncompleteTasks().asLiveData()
    }
    private val completedTask by lazy {
        todoDao.getCompletedTasks().asLiveData()
    }

    private var currentOwner: LifecycleOwner? = null
    private var currentObserver: Observer<List<TodoTasks>>? = null
    private var currentTaskType = TaskType.ALL

    fun observeData(lifecycleOwner: LifecycleOwner, observer: Observer<List<TodoTasks>>) {
        currentOwner = lifecycleOwner
        currentObserver = observer
        allTasks.observe(currentOwner!!, currentObserver!!)
    }

    fun sendEvent(event: Event) {
        when (event) {
            is InsertEvent -> {
                insertTodoTask(event)
            }
            is UpdateEvent -> {
                updateTodoTask(event)
            }
            is GetTasksEvent -> {
                handleGetTasks(event)
            }
        }
    }

    private fun updateTodoTask(event: UpdateEvent){
        viewModelScope.launch(Dispatchers.IO){
            todoDao.updateTodoTask(event.taskId, event.isCompleted)
        }
    }

    private fun insertTodoTask(event: InsertEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.insertTodoTask(event.todoTasks)
        }
    }

    private fun handleGetTasks(event: GetTasksEvent) {
        clearObserver()
        observeData(event.taskType)
        currentTaskType = event.taskType
    }

    private fun clearObserver(){
        when(currentTaskType){
            TaskType.ALL -> {
                currentObserver?.let { allTasks.removeObserver(it) }
            }
            TaskType.COMPLETED -> {
                currentObserver?.let { completedTask.removeObserver(it) }
            }
            TaskType.INCOMPLETE -> {
                currentObserver?.let { incompleteTasks.removeObserver(it) }
            }
        }
    }

    private fun observeData(newTaskType: TaskType){
        when(newTaskType){
            TaskType.ALL -> {
                currentObserver?.let { currentOwner?.let { lifecycleOwner -> allTasks.observe(lifecycleOwner, it) } }
            }
            TaskType.COMPLETED -> {
                currentObserver?.let {currentOwner?.let { lifecycleOwner -> completedTask.observe(lifecycleOwner, it) }}
            }
            TaskType.INCOMPLETE -> {
                currentObserver?.let {currentOwner?.let { lifecycleOwner -> incompleteTasks.observe(lifecycleOwner, it) } }
            }
        }
    }
}