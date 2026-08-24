package com.example.todolist.TodoList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.data.local.TodoRepository

// viewmodel()은 기본생성자만 호출할 수 있어서, 생성자에 repository 추가 했으니 팩토리 만들어 줘야함.
class TodoListViewModelFactory(
    private val repository: TodoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return TodoListViewModel(repository) as T
    }
}