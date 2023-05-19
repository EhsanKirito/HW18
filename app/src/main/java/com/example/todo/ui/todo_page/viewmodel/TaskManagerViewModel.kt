package com.example.todo.ui.todo_page.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.localdata.repository.Repository
import com.example.todo.localdata.entity.Task
import com.example.todo.ui.todo_page.enums.TaskState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskManagerViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var userId: Long = 0

    fun setUserId(id: Long) {
        userId = id
    }

    fun getUserId() = userId

    var state = TaskState.TODO

    private val _longClick = MutableLiveData(false)
    val longClick: LiveData<Boolean> = _longClick

    private var listTasksSelect = arrayListOf<Task>()

    private val _searchTask = MutableLiveData<List<Task>>()
    val searchTask: LiveData<List<Task>> = _searchTask

    fun searchTask(query: String) {
        viewModelScope.launch {
            _searchTask.postValue(repository.getTasksBySearch(query))
        }
    }

    fun listTodoTaskLiveData(): LiveData<List<Task>> {
        return repository.getTodoTasks(userId)
    }


    fun listDoingTaskLiveData(): LiveData<List<Task>> {
        return repository.getDoingTasks(userId)
    }

    fun listDoneTaskLiveData(): LiveData<List<Task>> {
        return repository.getDoneTasks(userId)
    }


    fun deleteAllTask() {
        viewModelScope.launch {
            repository.deleteAllTask(userId)
        }
    }


    fun addTask(
        task: Task
    ) {
        task.idUser = userId
        viewModelScope.launch {
            repository.insertTask(task)
        }
    }

    val clickLong: (Task) -> Unit = { task ->
        if (!longClick.value!!) {
            listTasksSelect.add(task)
            task.select = true
            _longClick.postValue(true)
        }
    }


    fun clickWhenLong(task: Task) {
        if (task in listTasksSelect) {
            listTasksSelect.remove(task)
            task.select = false
            if (listTasksSelect.isEmpty()) _longClick.postValue(false)
        } else {
            task.select = true
            listTasksSelect.add(task)
        }
    }

    fun unSelectAllTask() {
        for (task in listTasksSelect) {
            task.select = false
        }
        listTasksSelect = arrayListOf()
        _longClick.postValue(false)
    }

    fun selectAllTask() {
        when (state) {
            TaskState.TODO -> selectTaskInList(listTodoTaskLiveData().value!!)
            TaskState.DOING -> selectTaskInList(listDoingTaskLiveData().value!!)
            TaskState.DONE -> selectTaskInList(listDoneTaskLiveData().value!!)
        }
    }

    private fun selectTaskInList(listTask: List<Task>) {
        for (task in listTask) {
            task.select = true
            if (task !in listTasksSelect) {
                listTasksSelect.add(task)
            }
        }
    }

    fun getTask(id: Long): LiveData<Task> {
        return repository.getTask(id)
    }


    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }


    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }


    fun deleteSelectTask() {
        val listId: List<Long> = listTasksSelect.map { task ->
            task.id
        }
        viewModelScope.launch {
            repository.deleteSelectTask(listId)
        }

    }
}