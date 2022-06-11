package com.manabie.sonvh.domain

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.manabie.sonvh.model.dao.TodoTasksDao
import com.manabie.sonvh.model.entity.TodoTasks
import javax.inject.Inject

class LocalDbRepository @Inject constructor(val todoDao: TodoTasksDao) {

    private val allTasks by lazy {
        todoDao.getAllTodoTasks().asLiveData()
    }

    private val incompleteTasks by lazy {
       todoDao.getIncompleteTasks().asLiveData()
    }

    private val completedTask by lazy {
        todoDao.getCompletedTasks().asLiveData()
    }

    fun observeAllTasks(lifecycleOwner: LifecycleOwner, observer: Observer<List<TodoTasks>>){
        allTasks.observe(lifecycleOwner, observer)
    }

    fun observeCompletedTask(lifecycleOwner: LifecycleOwner, observer: Observer<List<TodoTasks>>){
        completedTask.observe(lifecycleOwner, observer)
    }

    fun observeIncompleteTask(lifecycleOwner: LifecycleOwner, observer: Observer<List<TodoTasks>>){
        incompleteTasks.observe(lifecycleOwner, observer)
    }

    fun removeAllTaskObserver(observer: Observer<List<TodoTasks>>){
        allTasks.removeObserver(observer)
    }

    fun removeCompletedTaskObserver(observer: Observer<List<TodoTasks>>){
        completedTask.removeObserver(observer)
    }

    fun removeIncompleteTaskObserver(observer: Observer<List<TodoTasks>>){
        incompleteTasks.removeObserver(observer)
    }
}