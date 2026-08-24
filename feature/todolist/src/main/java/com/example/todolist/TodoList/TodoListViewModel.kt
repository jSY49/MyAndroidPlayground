package com.example.todolist.TodoList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.local.TodoRepository
import com.example.todolist.model.Priority
import com.example.todolist.model.Todo
import com.example.todolist.model.TodoState
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import java.time.LocalDateTime
import java.util.UUID

class TodoListViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        TodoListUiState()
    )
    val uiState: StateFlow<TodoListUiState> = _uiState.asStateFlow()

    init {
        //DB의 flow를 구독해 채워주는 구조 (db가 변경되면 ui는 알아서 갱신된다)
        repository.todos
            .onEach { todos -> _uiState.update { it.copy(todos = todos) } }
            .launchIn(viewModelScope)
    }

    fun onFilterSelected(filter: TodoState) {
        _uiState.update { it.copy(selectedFilter = filter) }
    }

    fun addTodo(title: String, dueDateTime: LocalDateTime, priority: Priority) {

        viewModelScope.launch {
            repository.addTodo(
                Todo(
                    id = UUID.randomUUID().toString(),
                    title = title,
                    dueDateTime = dueDateTime,
                    priority = priority,
                    isDone = false
                )
            )
        }
    }

    fun onTodoCheckedChange(id: String, isDone: Boolean) {

        val todo = _uiState.value.todos.find { it.id == id } ?: return
        viewModelScope.launch {
            repository.updateTodo(todo.copy(isDone = isDone))
        }
    }

    fun updateTodo(id: String, title: String, dueDateTime: LocalDateTime, priority: Priority) {
        val todo = _uiState.value.todos.find { it.id == id } ?: return
        viewModelScope.launch {
            repository.updateTodo(todo.copy(title = title, dueDateTime = dueDateTime, priority = priority))
        }
    }

    private var lastDeletedTodo: Todo? = null

    fun onDelete(id: String) {

        val todo = _uiState.value.todos.find { it.id == id } ?: return
        lastDeletedTodo = todo
        viewModelScope.launch {
            repository.deleteTodo(todo)
        }

    }

    fun undoDelete() {
        val todo = lastDeletedTodo ?: return
        viewModelScope.launch {
            repository.addTodo(todo)
        }
        lastDeletedTodo = null
    }


}