package com.example.todo.localdata.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todo.localdata.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM user_table WHERE id = :id")
    fun getUser(id: Long): Flow<User>

    @Query("SELECT * FROM user_table WHERE user_name = :userName")
    fun getUserWithUsername(userName: String): Flow<User>

    @Query("SELECT * FROM user_table")
    fun getUsers(): Flow<List<User>>

    @Query("SELECT user_name FROM user_table")
    fun getAllUserNames(): Flow<List<String>>
}