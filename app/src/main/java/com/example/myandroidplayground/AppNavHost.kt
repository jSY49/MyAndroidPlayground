package com.example.myandroidplayground

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolist.TodoList.TodoListScreen

@Composable
fun TodoAppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier   // ← NavHost 자체가 modifier를 받음
    ) {
        composable("home") {
            NaviButtons(
                onTodoListClick = { navController.navigate("todoList") }
            )
        }
        composable("todoList") {
            TodoListScreen()
        }
    }
}