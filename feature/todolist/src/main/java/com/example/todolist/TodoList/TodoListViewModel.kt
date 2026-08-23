package com.example.todolist.TodoList

import androidx.lifecycle.ViewModel
import com.example.todolist.model.Priority
import com.example.todolist.model.Todo
import com.example.todolist.model.TodoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.selects.select
import java.time.LocalDateTime

class TodoListViewModel : ViewModel() {

    val sampleTodos = listOf(
        Todo(
            id = "1",
            title = "장보기",
            dueDateTime = LocalDateTime.now().plusHours(2),
            priority = Priority.HIGH,
            isDone = false
        ),
        Todo(
            id = "2",
            title = "운동하기",
            dueDateTime = LocalDateTime.now().minusHours(1),
            priority = Priority.MEDIUM,
            isDone = true
        ),
        Todo(
            id = "3",
            title = "보고서 작성",
            dueDateTime = LocalDateTime.now().plusDays(1).withHour(18).withMinute(0),
            priority = Priority.HIGH,
            isDone = false
        ),
        Todo(
            id = "4",
            title = "책 읽기",
            dueDateTime = LocalDateTime.now().plusDays(3),
            priority = Priority.LOW,
            isDone = false
        ),
        Todo(
            id = "5",
            title = "청소하기",
            dueDateTime = LocalDateTime.now().minusDays(1),
            priority = Priority.MEDIUM,
            isDone = true
        ),
        Todo(
            id = "6",
            title = "친구 만나기",
            dueDateTime = LocalDateTime.now().plusDays(2).withHour(19).withMinute(30),
            priority = Priority.LOW,
            isDone = false
        ),
    )

    private val _uiState = MutableStateFlow(
        TodoListUiState(todos = sampleTodos)
    )

    val uiState: StateFlow<TodoListUiState> = _uiState.asStateFlow()

    fun onFilterSelected(filter: TodoState) {
        _uiState.update { it.copy(selectedFilter = filter) }
    }

    fun onTodoCheckedChange(id : String , isDone : Boolean){
        _uiState.update { state ->
            state.copy(todos = state.todos.map {
                if(it.id == id) it.copy(isDone = isDone) else it
            })

        }
    }

    fun onDelete(id : String) {
        _uiState.update{ state ->
            state.copy(todos = state.todos.filterNot { it.id == id })
        }
    }
}