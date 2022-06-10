package com.manabie.sonvh

import android.app.Application
import com.manabie.sonvh.model.TodoDatabase

class TodoApplication: Application() {
    companion object{
        var INSTANCE: TodoApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
    val database by lazy { TodoDatabase.getDatabase(this) }
}