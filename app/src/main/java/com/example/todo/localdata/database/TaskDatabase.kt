package com.example.todo.localdata.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todo.localdata.dao.TaskDao
import com.example.todo.localdata.entity.Task
import com.example.todo.localdata.entity.TaskConvert

@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(TaskConvert::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}