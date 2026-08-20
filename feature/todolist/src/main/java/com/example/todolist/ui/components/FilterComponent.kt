package com.example.todolist.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolist.ui.theme.baseBlue
import com.example.todolist.ui.theme.sub_title_color
import org.w3c.dom.Text

@Composable
fun FilterComponent(
    text: String,
    checked : Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    Surface(
        border = if(checked) BorderStroke(1.dp , baseBlue) else BorderStroke(1.dp , Color.Gray),
        shape = RoundedCornerShape(50),
        color = if (checked) baseBlue else Color.Transparent

    ) {
        Text(
            text = text,
            modifier = modifier
                .padding(horizontal = 24.dp , vertical = 8.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = if(checked) Color.White else sub_title_color
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0x000000)
@Composable
fun FilterComponentPreview() {

    Row() {
        FilterComponent(
            "진행중",
            true,
            {}
        )
        FilterComponent(
            "완료",
            false,
            {}
        )
    }
}