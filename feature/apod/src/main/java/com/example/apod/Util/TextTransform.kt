package com.example.apod.Util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


fun LocalDate.toDateString(): String {
    return this.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일 EEEE", Locale.KOREAN))
}

fun String.toKoreanDateString(): String {
    val date = LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    return date.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일", Locale.KOREAN))
}