package com.example.todolist.TodoList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.R
import com.example.todolist.Util.toDateString
import com.example.todolist.model.Priority
import com.example.todolist.model.Todo
import com.example.todolist.model.TodoState
import com.example.todolist.ui.components.FilterComponent
import com.example.todolist.ui.components.TodoItem
import com.example.todolist.ui.theme.MyAndroidPlaygroundTheme
import com.example.todolist.ui.theme.bg
import com.example.todolist.ui.theme.sub_title_color
import com.example.todolist.ui.theme.title_color
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun TodoListScreen(
    modifier: Modifier = Modifier
) {

    val localDate = LocalDate.now();
    var selectedFilter : TodoState = TodoState.ALL
    val sampleTodos = listOf(
        Todo(
            id = "1",
            title = "장보기",
            dueDateTime = LocalDateTime.now().plusHours(2),
            priority = Priority.HIGH,
            isDone = false
        ),
        Todo(
            id = "2",
            title = "운동하기",
            dueDateTime = LocalDateTime.now().minusHours(1),
            priority = Priority.MEDIUM,
            isDone = true
        ),
        Todo(
            id = "3",
            title = "보고서 작성",
            dueDateTime = LocalDateTime.now().plusDays(1).withHour(18).withMinute(0),
            priority = Priority.HIGH,
            isDone = false
        ),
        Todo(
            id = "4",
            title = "책 읽기",
            dueDateTime = LocalDateTime.now().plusDays(3),
            priority = Priority.LOW,
            isDone = false
        ),
        Todo(
            id = "5",
            title = "청소하기",
            dueDateTime = LocalDateTime.now().minusDays(1),
            priority = Priority.MEDIUM,
            isDone = true
        ),
        Todo(
            id = "6",
            title = "친구 만나기",
            dueDateTime = LocalDateTime.now().plusDays(2).withHour(19).withMinute(30),
            priority = Priority.LOW,
            isDone = false
        ),
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(bg)
    ) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp , vertical = 16.dp)
        ) {
            Text(
                text = localDate.toDateString(),
                color = sub_title_color.copy(alpha = 0.6f),
                )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.todolist_title),
                color = title_color,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = modifier.padding(horizontal = 0.dp , vertical = 24.dp)
            ) {
                TodoState.entries.forEach { filter ->

                    val isSelected = filter == selectedFilter

                    FilterComponent(
                        filter.label,
                        isSelected,
                        {}
                    )
                    Spacer(modifier = modifier.width(16.dp))
                }
            }

            LazyColumn(modifier = modifier) {
                items(
                    items = sampleTodos , key = {it.id}
                ){ todo ->
                    TodoItem(
                        title = todo.title,
                        priority = todo.priority,
                        dueDateTime = todo.dueDateTime,
                        isDone = todo.isDone,
                        onCheckedChange = {},
                        onDelete = {}
                    )
                }
            }
        }

    }


}

@Preview(name = "Phone", device = "spec:width=360dp,height=800dp")
@Preview(name = "Phone_flip", device = "spec:width=360dp,height=880dp")
@Preview(name = "Tablet", device = "spec:width=1280dp,height=800dp")
@Preview(name = "Foldable", device = "spec:width=673dp,height=841dp")
@Composable
fun TodoListScreenPreview() {

    MyAndroidPlaygroundTheme() {
        TodoListScreen()
    }
}