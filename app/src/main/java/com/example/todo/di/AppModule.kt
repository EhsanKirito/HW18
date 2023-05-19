package com.example.todo.di

import android.app.Application
import androidx.room.Room
import com.example.todo.localdata.repository.Repository
import com.example.todo.localdata.dao.TaskDao
import com.example.todo.localdata.database.TaskDatabase
import com.example.todo.localdata.dao.UserDao
import com.example.todo.localdata.database.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserDatabase(application: Application): UserDatabase {
        return Room.databaseBuilder(application, UserDatabase::class.java, "user_database").build()
    }

    @Provides
    fun provideUserDao(userDatabase: UserDatabase): UserDao {
        return userDatabase.userDao()
    }

    @Provides
    fun provideTaskDatabase(application: Application): TaskDatabase =
        Room.databaseBuilder(application, TaskDatabase::class.java, "task_database").build()


    @Provides
    fun provideTaskDao(taskDatabase: TaskDatabase): TaskDao {
        return taskDatabase.taskDao()
    }

    @Provides
    @Singleton
    fun provideRepository(
        userDao: UserDao,
        taskDao: TaskDao
    ): Repository {
        return Repository(userDao, taskDao)
    }
}