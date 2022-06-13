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

/**
 * Test class for local database transactions
 */
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
        // Create a todoTask
        val todoTask = TodoTasks(
            task = "test",
            uid = 1
        )
        // Insert to db
       val result =  todoTasksDao.insertTodoTask(todoTask)
        // Check if the inserted rowId == todoTask uid
        Assert.assertEquals(1L, result)
        // Search in the database the task with uid == 1 to check if it exist
        val taskInserted = todoTasksDao.searchTodoTask(1)
        Assert.assertEquals(1, taskInserted.uid)
    }

    @Test
    @Throws(Exception::class)
    fun testUpdateTodoTask() {
        // Create a todoTask
        val todoTask = TodoTasks(
            task = "test",
            uid = 1,
            isCompleted = false
        )
        // insert into db
         todoTasksDao.insertTodoTask(todoTask)
        // Update the todoTask with uid == 1
        val trueResult = todoTasksDao.updateTodoTask(1, true)
        // Verify if it is updated
        Assert.assertEquals(1, trueResult)
        // Search if the todoTask with uid == 1 exist
       val itemUpdated = todoTasksDao.searchTodoTask(1)
        Assert.assertEquals(true, itemUpdated.isCompleted)
        // Try to update a todoTask that is not inserted yet
        val notFoundResult =  todoTasksDao.updateTodoTask(2, false)
        // verify if the result is 0 rows effected
        Assert.assertEquals(0, notFoundResult)
    }
}