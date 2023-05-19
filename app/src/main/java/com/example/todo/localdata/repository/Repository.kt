package com.example.todo.localdata.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.todo.localdata.dao.TaskDao
import com.example.todo.localdata.entity.User
import com.example.todo.localdata.dao.UserDao
import com.example.todo.localdata.entity.Task
import javax.inject.Inject

class Repository @Inject constructor(
    private val userDao: UserDao,
    private val taskDao: TaskDao
) {



    //        for user
    fun getUser(id: Long): LiveData<User> {
        return userDao.getUser(id).asLiveData()
    }

    fun getUsers(): LiveData<List<User>> {
        return userDao.getUsers().asLiveData()
    }

    fun getAllUserName(): LiveData<List<String>> {
        return userDao.getAllUserNames().asLiveData()
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }


    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    fun getUserByUserName(username: String): LiveData<User> {
        return userDao.getUserWithUsername(username).asLiveData()
    }

    // for task

    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }


    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun deleteAllTask(idUser: Long) {
        taskDao.deleteAllTask(idUser)
    }

    suspend fun deleteSelectTask(tasksId: List<Long>) {
        taskDao.deleteSelectTask(tasksId)
    }

    fun getTask(id: Long): LiveData<Task> {
        return taskDao.getTask(id).asLiveData()
    }

    fun getTasks(idUser: Long): LiveData<List<Task>> {
        return taskDao.getTasks(idUser).asLiveData()
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    fun getTodoTasks(idUser: Long): LiveData<List<Task>> {
        return taskDao.getListTodo(idUser).asLiveData()
    }

    fun getDoingTasks(idUser: Long): LiveData<List<Task>> {
        return taskDao.getListDoing(idUser).asLiveData()
    }

    fun getDoneTasks(idUser: Long): LiveData<List<Task>> {
        return taskDao.getListDone(idUser).asLiveData()
    }

    suspend fun getTasksBySearch(querySearch: String): List<Task> {
        return taskDao.searchTask(querySearch)
    }


}