package com.example.todolist.Util

import java.text.DateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDateTime.toDisplayString(): String {

    val today = LocalDate.now()
    val datePart = when (this.toLocalDate()) {
        today -> "오늘"
        today.plusDays(1) -> "내일"
        else -> this.format(DateTimeFormatter.ofPattern("M월 d일"))
    }
    val timePart = this.format(DateTimeFormatter.ofPattern("a h:mm", Locale.KOREAN))
    return "$datePart · $timePart"

}

fun LocalDateTime.toTimeString(): String {
    return this.format(DateTimeFormatter.ofPattern("a h:mm", Locale.KOREAN))
    // 결과: "오전 10:30"
}

fun LocalDate.toDateString(): String {
    return this.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일 EEEE", Locale.KOREAN))
}