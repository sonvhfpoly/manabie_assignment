package com.manabie.sonvh

import android.content.Context
import androidx.room.Room
import com.manabie.sonvh.model.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            TodoDatabase::class.java,
            "todotasks.db"
        )
            .build()
    }

    @Singleton
    @Provides
    fun provideTodoTasksDao(todoDatabase: TodoDatabase) = todoDatabase.todoTasksDao()
}