package com.example.todo.localdata.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity("adminTable")
data class Admin(
    @ColumnInfo("username")
    val userName: String,
    @ColumnInfo("password")
    val password: String
)