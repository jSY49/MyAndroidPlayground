package com.example.myandroidplayground

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NaviButtons(modifier : Modifier = Modifier,
                onNavigate: (String) -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        var buttonModifier = modifier.padding(8.dp)
        var buttonColor = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF2A2A2A),   // 배경색
            contentColor = Color.White             // 텍스트/아이콘 색
        )

        LazyVerticalGrid(
            modifier = modifier.background(Color.Black),
            columns = GridCells.Fixed(3)
        ) {
            item {
                Button(
                    modifier = buttonModifier,
                    colors = buttonColor,
                    onClick = { onNavigate("todoList") }
                ) { Text("TODO") }
            }

            item {
                Button(
                    modifier = buttonModifier,
                    colors = buttonColor,
                    onClick = { onNavigate("apod") }
                ) { Text("NASA") }
            }
        }
    }
}