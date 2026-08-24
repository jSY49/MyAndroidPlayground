package com.example.todolist.data.local

import com.example.todolist.model.Todo

//TodoEntity(DB 모델) ↔ Todo(도메인/UI 모델) 매퍼. DB 스키마와 UI가 쓰는 모델을 분리해두는 경계.
fun TodoEntity.toDomain(): Todo = Todo(
    id = id,
    title = title,
    dueDateTime = dueDateTime,
    priority = priority,
    isDone = isDone
)

fun Todo.toEntity(): TodoEntity = TodoEntity(
    id = id,
    title = title,
    dueDateTime = dueDateTime,
    priority = priority,
    isDone = isDone
)