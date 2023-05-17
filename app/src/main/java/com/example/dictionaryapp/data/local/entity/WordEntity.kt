package com.example.dictionaryapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class WordEntity(
    @ColumnInfo(name = "english_word")
    val englishWord: String,
    @ColumnInfo(name = "persian_word")
    val farsiWord: String,
    @ColumnInfo(name = "french_word")
    val frenchWord: String,
    @ColumnInfo(name = "arabic_word")
    val arbicWord: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
