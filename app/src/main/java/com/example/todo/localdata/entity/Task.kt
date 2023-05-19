package com.example.todo.localdata.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.todo.ui.todo_page.enums.TaskState

@Entity(tableName = "task_table")
data class Task(
    @ColumnInfo("title") var title: String,
    @ColumnInfo("description") var description: String,
    @ColumnInfo("date") var date: String,
    @ColumnInfo("time") var time: String,
    @ColumnInfo("state") var state: TaskState,
    @ColumnInfo("id_user") var idUser: Long,
    @ColumnInfo("image_src") var imageSrc: String? = null,
    @ColumnInfo("id") @PrimaryKey(true) val id: Long = 0L,
    @ColumnInfo("select") var select: Boolean = false,
)

class TaskConvert {
    @TypeConverter
    fun fromState(state: TaskState): String = state.name

    @TypeConverter
    fun toState(state: String): TaskState = TaskState.valueOf(state)
}
