package com.example.dictionaryapp.data.model.ui

import kotlinx.serialization.Serializable


@Serializable
data class Word(
    var english: String,
    var farsi: String,
    var french: String,
    var arabic: String,
    val id: Int = 0,
)


