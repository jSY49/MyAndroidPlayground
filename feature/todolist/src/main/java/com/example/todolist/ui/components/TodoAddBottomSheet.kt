package com.example.todolist.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolist.R
import com.example.todolist.Util.toColor
import com.example.todolist.Util.toDisplayString
import com.example.todolist.model.Priority
import com.example.todolist.ui.theme.MyAndroidPlaygroundTheme
import com.example.todolist.ui.theme.sub_title_color
import com.example.todolist.ui.theme.title_color
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoAddBottomSheet(
    modifier: Modifier = Modifier,
    onAddTodo: (title: String, dueDateTime: LocalDateTime, priority: Priority) -> Unit = { _, _, _ -> },
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    fun dismissKeyboard() {
        focusManager.clearFocus()
        keyboardController?.hide()
    }

    // 1. 할 일 제목
    var title by remember { mutableStateOf("") }

    // 2. 마감 시간
    var dueDateTime by remember { mutableStateOf(LocalDateTime.now().withHour(18).withMinute(0)) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    // 3. 우선순위
    var priority by remember { mutableStateOf(Priority.MEDIUM) }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = Color.Transparent,
    ) {

        Column(
            modifier = Modifier.imePadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // 1. 할 일 제목
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = { title = it },
                shape = RoundedCornerShape(20f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,      // 포커스(입력 중)일 때 테두리
                    unfocusedBorderColor = Color.Gray,             // 포커스 안 됐을 때 테두리
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.todolist_add_item_placeholder),
                        color = sub_title_color
                    )
                }
            )

            // 텍스트 필드가 아닌 다른 영역을 누르면 포커스/키보드 해제
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { dismissKeyboard() },
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // 2. 마감 시간
                DueDateTimeRow(
                    dueDateTime,
                    onClick = {
                        dismissKeyboard()
                        showDatePicker = true
                    }
                )

                // 3. 우선순위
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.todolist_add_item_priority),
                        color = title_color
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        listOf(Priority.HIGH, Priority.MEDIUM, Priority.LOW).forEach { entry ->
                            PriorityChip(
                                priority = entry,
                                selected = entry == priority,
                                onClick = {
                                    dismissKeyboard()
                                    priority = entry
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // 4. 추가하기 버튼
                val isTitleValid = title.isNotBlank()

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = isTitleValid) {
                            dismissKeyboard()
                            onAddTodo(title.trim(), dueDateTime, priority)
                            title = ""
                            dueDateTime = LocalDateTime.now().withHour(18).withMinute(0)
                            priority = Priority.MEDIUM
                        },
                    shape = RoundedCornerShape(16.dp),
                    color = if (isTitleValid) Color.White else Color(0xFF3A3A3A)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.todolist_add_item_add),
                            color = if (isTitleValid) Color.Black else sub_title_color,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

    // 2-1. 마감 시간 - 날짜 선택 다이얼로그
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = dueDateTime
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val newDate = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        dueDateTime = LocalDateTime.of(newDate, dueDateTime.toLocalTime())
                    }
                    showDatePicker = false
                    showTimePicker = true   // 날짜 선택 후 바로 시간 선택으로 이어짐
                }) { Text("다음") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("취소") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // 2-2. 마감 시간 - 시간 선택 다이얼로그
    if (showTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = dueDateTime.hour,
            initialMinute = dueDateTime.minute
        )

        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    dueDateTime = dueDateTime
                        .withHour(timePickerState.hour)
                        .withMinute(timePickerState.minute)
                    showTimePicker = false
                }) { Text("확인") }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) { Text("취소") }
            },
            text = {
                TimePicker(state = timePickerState)
            }
        )
    }
}


@Composable
fun DueDateTimeRow(
    dueDateTime: LocalDateTime,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFF2A2A2A)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.todolist_add_item_endDate),
                color = Color.White
            )

            Text(
                text = dueDateTime.toDisplayString(),   // "오늘 · 오후 6:00" 포맷 함수
                color = Color(0xFF4A90E2),               // 파란색 강조
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun PriorityChip(
    priority: Priority,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val priorityColor = priority.toColor()

    Surface(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = if (selected) priorityColor else Color.Transparent,
        border = BorderStroke(1.dp, priorityColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = priority.label,
                color = if (selected) Color.Black else priorityColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
@Preview(name = "Phone", device = "spec:width=360dp,height=800dp")
@Preview(name = "Phone_flip", device = "spec:width=360dp,height=880dp")
@Preview(name = "Tablet", device = "spec:width=1280dp,height=800dp")
@Preview(name = "Foldable", device = "spec:width=673dp,height=841dp")
fun TodoAddBottomSheetPreview() {

    MyAndroidPlaygroundTheme() {
        TodoAddBottomSheet()
    }
}
