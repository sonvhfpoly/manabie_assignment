package com.manabie.sonvh

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
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
class TodoTasksDatabaseTest {
    private lateinit var todoTasksDao: TodoTasksDao
    private lateinit var db: TodoDatabase

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, TodoDatabase::class.java).build()
        todoTasksDao = db.todoTasksDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testCreateTodoTask() {
        val todoTask = TodoTasks(
            task = "test",
            uid = 1
        )
       val result =  todoTasksDao.insertTodoTask(todoTask)
        Assert.assertEquals(1L, result)

        val taskInserted = todoTasksDao.searchTodoTask(1)
        Assert.assertEquals(1, taskInserted.uid)
    }

    @Test
    @Throws(Exception::class)
    fun testUpdateTodoTask() {
        val todoTask = TodoTasks(
            task = "test",
            uid = 1,
            isCompleted = false
        )
         todoTasksDao.insertTodoTask(todoTask)
        val trueResult = todoTasksDao.updateTodoTask(1, true)
        Assert.assertEquals(1, trueResult)

       val itemUpdated = todoTasksDao.searchTodoTask(1)
        Assert.assertEquals(true, itemUpdated.isCompleted)

        val notFoundResult =  todoTasksDao.updateTodoTask(2, false)
        Assert.assertEquals(0, notFoundResult)
    }
}