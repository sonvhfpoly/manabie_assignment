package com.manabie.sonvh.domain

import com.manabie.sonvh.model.entity.TodoTasks

interface Event

class InsertEvent(val todoTasks: TodoTasks): Event

class UpdateEvent(val todoTasks: TodoTasks): Event

class GetTasksEvent(val taskType: TaskType): Event

enum class TaskType{
    ALL,
    COMPLETED,
    INCOMPLETE
}