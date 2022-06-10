package com.manabie.sonvh.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoTasks(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "completed") val isCompleted: Boolean,
    @ColumnInfo(name = "task") val task: String
)