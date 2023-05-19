package com.example.todo.localdata.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todo.localdata.entity.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query("DELETE FROM task_table WHERE id_user = :idUser")
    suspend fun deleteAllTask(idUser: Long)

    @Query("DELETE FROM task_table WHERE id  IN (:tasksId)")
    suspend fun deleteSelectTask(tasksId: List<Long>)

    @Query("SELECT * FROM task_table WHERE id = :id")
    fun getTask(id: Long): Flow<Task>

    @Query("SELECT * FROM task_table WHERE id_user = :idUser")
    fun getTasks(idUser: Long): Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE title LIKE '%' || :textSearch || '%'OR description LIKE '%' || :textSearch || '%' OR time LIKE '%' || :textSearch || '%' OR date LIKE '%' || :textSearch || '%'")
    suspend fun searchTask(textSearch: String): List<Task>

    @Query("SELECT * FROM task_table WHERE id_user = :idUser AND state = 'TODO'")
    fun getListTodo(idUser: Long): Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE id_user = :idUser AND state = 'DOING'")
    fun getListDoing(idUser: Long): Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE id_user = :idUser AND state = 'DONE'")
    fun getListDone(idUser: Long): Flow<List<Task>>
}