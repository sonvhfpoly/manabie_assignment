package com.manabie.sonvh.domain

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manabie.sonvh.model.entity.TodoTasks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor( private val localDbRepository: LocalDbRepository): ViewModel(){

    private var currentOwner: LifecycleOwner? = null
    private var currentObserver: Observer<List<TodoTasks>>? = null
    private var currentTaskType = TaskType.ALL

    fun observeData(lifecycleOwner: LifecycleOwner, observer: Observer<List<TodoTasks>>) {
        currentOwner = lifecycleOwner
        currentObserver = observer
        localDbRepository.observeAllTasks(currentOwner!!, currentObserver!!)
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
            localDbRepository.todoDao.updateTodoTask(event.taskId, event.isCompleted)
        }
    }

    private fun insertTodoTask(event: InsertEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            localDbRepository.todoDao.insertTodoTask(event.todoTasks)
        }
    }

    private fun handleGetTasks(event: GetTasksEvent) {
        clearObserver()
        observeData(event.taskType)
        currentTaskType = event.taskType
    }

    private fun clearObserver(){
        viewModelScope.launch (Dispatchers.Main){
            when(currentTaskType){
                TaskType.ALL -> {
                    currentObserver?.let { localDbRepository.removeAllTaskObserver(it)}
                }
                TaskType.COMPLETED -> {
                    currentObserver?.let { localDbRepository.removeCompletedTaskObserver(it) }
                }
                TaskType.INCOMPLETE -> {
                    currentObserver?.let { localDbRepository.removeIncompleteTaskObserver(it)}
                }
            }
        }
    }

    private fun observeData(newTaskType: TaskType){
        viewModelScope.launch (Dispatchers.Main){
            when(newTaskType){
                TaskType.ALL -> {
                    currentObserver?.let { currentOwner?.let { lifecycleOwner -> localDbRepository.observeAllTasks(lifecycleOwner, it) } }
                }
                TaskType.COMPLETED -> {
                    currentObserver?.let {currentOwner?.let { lifecycleOwner -> localDbRepository.observeCompletedTask(lifecycleOwner, it) }}
                }
                TaskType.INCOMPLETE -> {
                    currentObserver?.let {currentOwner?.let { lifecycleOwner -> localDbRepository.observeIncompleteTask(lifecycleOwner, it) } }
                }
            }
        }
    }
}