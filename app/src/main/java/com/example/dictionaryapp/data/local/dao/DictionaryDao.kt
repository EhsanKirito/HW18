package com.example.dictionaryapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dictionaryapp.data.local.entity.WordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DictionaryDao {
    @Insert
    suspend fun insert(wordEntity: WordEntity)

    @Delete
    suspend fun delete(wordEntity: WordEntity)

    @Update
    suspend fun update(wordEntity: WordEntity)

    @Query("SELECT * FROM word_table")
    fun getWords(): Flow<List<WordEntity>>

    @Query(
        """
        SELECT * FROM word_table
        WHERE english_word Like :searchText
        OR persian_word LIKE :searchText 
        OR french_word LIKE :searchText 
        OR arabic_word LIKE :searchText
        """
    )
    fun searchWord(searchText: String): Flow<List<WordEntity>>

    @Query("SELECT * FROM word_table WHERE id = :id")
    fun getWord(id: Int): WordEntity

}