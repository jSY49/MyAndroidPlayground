package com.example.todolist.TodoList

import com.example.todolist.model.Todo
import com.example.todolist.model.TodoState

data class TodoListUiState(
    val todos: List<Todo> = emptyList(),
    val selectedFilter: TodoState = TodoState.ALL
) {
    val filteredTodos: List<Todo>
        get() = when (selectedFilter) {
            TodoState.ALL -> todos
            TodoState.IN_PROGRESS -> todos.filter {!it.isDone }
            TodoState.DONE -> todos.filter { it.isDone }
        }
}