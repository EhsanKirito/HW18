package com.example.todo.localdata.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_table")
data class User(
    @ColumnInfo("user_name")
    var userName: String,
    @ColumnInfo("password")
    var password: String,
    @ColumnInfo("date_signup")
    var dateSignup: String,
    @ColumnInfo("id")
    @PrimaryKey(true)
    val id: Long = 0
)
//fun User.equalUser(user: User): Boolean {
//    return user.userName == this.userName && user.password == this.password
//}