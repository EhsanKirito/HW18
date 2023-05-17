package com.example.dictionaryapp.util

fun checkWordsValid(english: String, farsi: String, french: String, arabic: String): Boolean {
    return !(english.isBlank() || farsi.isBlank() || french.isBlank() || arabic.isBlank())
}