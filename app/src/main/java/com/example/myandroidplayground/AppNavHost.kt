package com.example.myandroidplayground

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apod.Screen.MyNasaExplorerApp
import com.example.todolist.TodoList.TodoListScreen

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier   // ← NavHost 자체가 modifier를 받음
    ) {
        composable("home") {
            NaviButtons(
                onNavigate = { route -> navController.navigate(route) }
            )
        }
        composable("todoList") {
            TodoListScreen()
        }
        composable("apod") {
            MyNasaExplorerApp()
        }
    }
}