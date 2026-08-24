package com.example.todolist.data.local

import com.example.todolist.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//ViewModel에 Todo 도메인 모델로 노출하는 창구
class TodoRepository(private val dao: TodoDao) {
    val todos: Flow<List<Todo>> = dao.getAll().map { list -> list.map { it.toDomain() } }
    suspend fun addTodo(todo: Todo) = dao.insert(todo.toEntity())
    suspend fun updateTodo(todo: Todo) = dao.update(todo.toEntity())
    suspend fun deleteTodo(todo: Todo) = dao.delete(todo.toEntity())
}