package com.example.todolist.model

import java.time.LocalDateTime

data class Todo(
    val id: String,
    val title: String,
    val dueDateTime : LocalDateTime,
    val priority : Priority,
    val isDone: Boolean
)