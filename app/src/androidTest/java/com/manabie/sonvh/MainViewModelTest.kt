package com.manabie.sonvh

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.manabie.sonvh.domain.InsertEvent
import com.manabie.sonvh.domain.LocalDbRepository
import com.manabie.sonvh.domain.MainViewModel
import com.manabie.sonvh.domain.UpdateEvent
import com.manabie.sonvh.model.TodoDatabase
import com.manabie.sonvh.model.dao.TodoTasksDao
import com.manabie.sonvh.model.entity.TodoTasks
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    private lateinit var todoTasksDao: TodoTasksDao
    private lateinit var db: TodoDatabase
    private lateinit var localdbRepository: LocalDbRepository
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, TodoDatabase::class.java
        ).build()
        todoTasksDao = db.todoTasksDao()
        localdbRepository = LocalDbRepository(todoTasksDao)
        mainViewModel = MainViewModel(localdbRepository)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testSendInsertEvent() {
        // Create a todoTask
        val todoTask = TodoTasks(
            uid = 12,
            isCompleted = false,
            task = "test insert"
        )
        // Create an insert event
        val insertEvent = InsertEvent(todoTask)
        // Send insertEvent to viewmodel
        mainViewModel.sendEvent(insertEvent)
        // verify if the todoTask is inserted
        val result = todoTasksDao.searchTodoTask(12)
        Assert.assertEquals(result.task, "test insert")
    }

    @Test
    fun testUpdateEvent() {
        // Create a todoTask
        val todoTask = TodoTasks(
            uid = 17,
            isCompleted = false,
            task = "test update"
        )
        // Create an insert event
        val insertEvent = InsertEvent(todoTask)
        // Send insertEvent to viewmodel
        mainViewModel.sendEvent(insertEvent)

        // Create an update event
        val updateEvent = UpdateEvent(17, true)
        // send update event to viewmodel
        mainViewModel.sendEvent(updateEvent)

        // verify if the todoTask is updated
        val result = todoTasksDao.searchTodoTask(17)
        Assert.assertEquals(result.isCompleted, true)
    }

}