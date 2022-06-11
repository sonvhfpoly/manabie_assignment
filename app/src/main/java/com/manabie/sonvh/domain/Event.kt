package com.manabie.sonvh.domain

import com.manabie.sonvh.model.entity.TodoTasks

interface Event

class InsertEvent(val todoTasks: TodoTasks): Event

class UpdateEvent(val taskId: Int, val isCompleted: Boolean): Event

class GetTasksEvent(val taskType: TaskType): Event

enum class TaskType{
    ALL,
    COMPLETED,
    INCOMPLETE
}