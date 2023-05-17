package com.example.dictionaryapp.util

import com.example.dictionaryapp.data.local.entity.WordEntity
import com.example.dictionaryapp.data.model.ui.Word


fun List<WordEntity>.toWord(): List<Word> =
    this.map { wordEntity ->
        wordEntity.asWord()
    }


fun WordEntity.asWord() = Word(englishWord, farsiWord, frenchWord, arbicWord, id)

fun Word.asWordEntity() = WordEntity(english, farsi, french, arabic, id)
