package com.example.todolist.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.Util.toColor
import com.example.todolist.Util.toDisplayString
import com.example.todolist.Util.toTimeString
import com.example.todolist.model.Priority
import com.example.todolist.ui.theme.MyAndroidPlaygroundTheme
import com.example.todolist.ui.theme.item_bg
import com.example.todolist.ui.theme.sub_title_color
import com.example.todolist.ui.theme.title_color
import java.time.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoItem(
    title: String,
    dueDateTime: LocalDateTime,
    priority: Priority,
    isDone: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {

    val alpha by animateFloatAsState(   // by : 위임. 원래는 State<Float> 타입이 반환되는데, by를 쓰면 .value를 매번 안 써도 alpha를 그냥 Float처럼 바로 쓸 수 있게 해줌
        targetValue = if (isDone) 0.5f else 1f, // isdone이 바뀔 때 마다
        animationSpec = tween(300), //tween : 일정시간 동안 값을 부드럽게 interpolate
        label = "alpha"// 디버깅/ 애니메이션 검사 도구에서 구분하기 위한 이름표
    )

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onDelete()
            }
            false
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Red),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "삭제",
                    tint = Color.White,
                    modifier = Modifier.padding(end = 24.dp)
                )
            }
        }
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
//            .background(item_bg)  //surface는 color속성으로 배경 색상 변경
            ,
            shape = RoundedCornerShape(16.dp),
            color = item_bg
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                CustomCheckBox(
                    checked = isDone,
                    onCheckedChange = onCheckedChange,
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = title_color.copy(alpha = if (isDone) 0.5f else 1f),
                            textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None,
                        ),
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${dueDateTime.toDisplayString()} · 우선순위 ${priority.label}",
                        fontSize = 13.sp,
                        color = sub_title_color.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }

                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(priority.toColor())
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoItemPreview() {
    MyAndroidPlaygroundTheme {
        Column {
            TodoItem(
                title = "장보기",
                priority = Priority.HIGH,
                dueDateTime = LocalDateTime.now(),
                isDone = false,
                onCheckedChange = {},
                onDelete = {}
            )
            TodoItem(
                title = "운동하기",
                priority = Priority.MEDIUM,
                dueDateTime = LocalDateTime.now(),
                isDone = true,
                onCheckedChange = {},
                onDelete = {}
            )
        }
    }
}