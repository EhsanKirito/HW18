package com.example.dictionaryapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dictionaryapp.data.local.dao.DictionaryDao
import com.example.dictionaryapp.data.local.entity.WordEntity


@Database(entities = [WordEntity::class], version = 1, exportSchema = false)
abstract class DictionaryDatabase : RoomDatabase() {
    abstract fun dictionaryDao(): DictionaryDao
}