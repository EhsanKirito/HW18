package com.example.dictionaryapp.di

import android.app.Application
import androidx.room.Room
import com.example.dictionaryapp.data.local.dao.DictionaryDao
import com.example.dictionaryapp.data.local.database.DictionaryDatabase
import com.example.dictionaryapp.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun provideDataBase(application: Application): DictionaryDatabase =
        Room.databaseBuilder(application, DictionaryDatabase::class.java, "dictionary_database")
            .build()


    @Provides
    fun provideDictionaryDao(dictionaryDatabase: DictionaryDatabase): DictionaryDao =
        dictionaryDatabase.dictionaryDao()


    @Provides
    fun provideRepository(dictionaryDao: DictionaryDao) = Repository(dictionaryDao)
}