package com.example.dictionaryapp.ui.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.data.repository.Repository
import com.example.dictionaryapp.data.model.ui.Word
import com.example.dictionaryapp.ui.features.main.enums.LanguageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _languageState = MutableLiveData(LanguageState.ENGLISH)
    val languageState: LiveData<LanguageState> = _languageState


    private var _wordListLive = MutableLiveData<List<Word>>()
    var wordListLive: LiveData<List<Word>> = _wordListLive


    private val _word = MutableLiveData<Word>()
    val word: LiveData<Word> = _word


    init {
        getWordsBySearch("")
    }

    fun getWord(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _word.postValue(repository.getWord(id))
        }
    }

    fun getWordsBySearch(textSearch: String) {
        viewModelScope.launch {
            repository.searchWord(textSearch).collect { words ->
                _wordListLive.postValue(words)
            }
        }
    }

    private fun insertWord(word: Word) {
        viewModelScope.launch {
            repository.insert(word)
        }
    }

    fun addWord(enWord: String, prWord: String, frWord: String, arWord: String) {
        insertWord(Word(enWord, prWord, frWord, arWord))
    }


    fun deleteWord(word: Word) {
        viewModelScope.launch {
            repository.delete(word)
        }
    }

    fun updateWord(word: Word) {
        viewModelScope.launch {
            repository.update(word)
        }
    }

    fun changeLanguage(newLanguageState: LanguageState) {
        _languageState.postValue(newLanguageState)
    }
}