package com.example.dictionaryapp.data.repository

import com.example.dictionaryapp.data.local.dao.DictionaryDao
import com.example.dictionaryapp.data.model.ui.Word
import com.example.dictionaryapp.util.asWordEntity
import com.example.dictionaryapp.util.asWord
import com.example.dictionaryapp.util.toWord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class Repository(private val dictionaryDao: DictionaryDao) {

    fun getWord(id: Int): Word = dictionaryDao.getWord(id).asWord()


    fun getWords(): Flow<List<Word>> =
        dictionaryDao.getWords().map { wordEntities ->
            wordEntities.toWord()
        }


    fun searchWord(searchText: String): Flow<List<Word>> =
        dictionaryDao.searchWord("%$searchText%").map { wordEntities ->
            wordEntities.toWord()
        }


    suspend fun delete(word: Word) {
        dictionaryDao.delete(word.asWordEntity())
    }

    suspend fun insert(word: Word) {
        dictionaryDao.insert(word.asWordEntity())
    }

    suspend fun update(word: Word) {
        dictionaryDao.update(word.asWordEntity())
    }



}