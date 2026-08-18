package com.example.todoapplication.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolist.ui.theme.MyAndroidPlaygroundTheme

@Composable
fun TodoItem(
    title: String,
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

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
//        tonalElevation = 1.dp
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isDone,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.outline
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = alpha)  // copy(alpha = alpha) : 색상은 그대로 투명도만 바꾸기
                ),
                modifier = Modifier.weight(1f),
//                textAlign = TextAlign.Center
            )

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "삭제",
                    tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Preview
@Composable
fun TodoItemPreview() {
    MyAndroidPlaygroundTheme {
        Column {
            TodoItem(
                title = "장보기",
                isDone = false,
                onCheckedChange = {},
                onDelete = {}
            )
            TodoItem(
                title = "운동하기",
                isDone = true,
                onCheckedChange = {},
                onDelete = {}
            )
        }
    }
}