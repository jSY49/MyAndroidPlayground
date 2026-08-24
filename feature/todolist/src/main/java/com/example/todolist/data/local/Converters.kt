package com.example.todolist.data.local

import androidx.room.TypeConverter
import com.example.todolist.model.Priority
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

//Room 데이터 저장 타입 전환
class Converters {

    @TypeConverter
    fun fromEpochMillis(value: Long?): LocalDateTime? =
        value?.let { LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault()) }

    @TypeConverter
    fun toEpochMillis(dateTime: LocalDateTime?): Long? =
        dateTime?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()

    @TypeConverter
    fun fromPriorityName(value: String?): Priority? = value?.let { Priority.valueOf(it) }

    @TypeConverter
    fun toPriorityName(priority: Priority?): String? = priority?.name
}