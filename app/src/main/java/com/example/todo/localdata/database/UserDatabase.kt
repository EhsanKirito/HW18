package com.example.todo.localdata.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todo.localdata.entity.User
import com.example.todo.localdata.dao.UserDao

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}