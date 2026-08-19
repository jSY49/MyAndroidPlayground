package com.example.todolist.Util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.todolist.model.Priority

@Composable
fun Priority.toColor(): Color = when (this) {
    Priority.HIGH -> Color(0xFFE53935)     // 빨강
    Priority.MEDIUM -> Color(0xFFFFB300)   // 노랑/주황
    Priority.LOW -> Color(0xFF43A047)      // 초록
}