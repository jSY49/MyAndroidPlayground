package com.example.todolist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todolist.model.Priority
import java.time.LocalDateTime

//테이블 스키마
@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val dueDateTime: LocalDateTime,
    val priority: Priority,
    val isDone: Boolean
)