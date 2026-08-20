package com.example.todolist.model

enum class TodoState(val label: String) {
    ALL("전체"),
    IN_PROGRESS("진행중"),
    DONE("완료")
}