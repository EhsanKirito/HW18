package com.example.todo.ui.login_page.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.localdata.entity.Admin
import com.example.todo.localdata.repository.Repository
import com.example.todo.localdata.entity.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val admin = Admin("ehsan", "1")


    val userName = MutableLiveData("")
    val password = MutableLiveData("")


    fun oldUser(userName: String): LiveData<User> {
        return repository.getUserByUserName(userName)
    }


    private fun provideUser(): User {
        return User(userName.value!!, password.value!!, getTimeAndDate())
    }

    fun addNewUser() {
        viewModelScope.launch {
            repository.insertUser(provideUser())
        }
    }

    fun checkEntryValid(): Boolean {
        return !(userName.value!!.isBlank() || password.value!!.isBlank())
    }


    fun getTimeAndDate(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        return current.format(formatter).toString()
    }
}