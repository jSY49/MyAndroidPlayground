package com.example.apod.ui.Components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apod.ui.theme.mainColor

@Composable
fun ApodActionButtons(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()   // 지금 눌리고 있는지

    Surface(
        onClick = onClick,
        modifier = modifier,
        interactionSource = interactionSource,
        shape = RoundedCornerShape(percent = 50),
        color = if (isPressed) Color.Transparent else Color(0xFF2A2A2A),
        border = if (isPressed) BorderStroke(1.dp, mainColor) else BorderStroke(1.dp, Color.LightGray)
    ) {
        Text(
            text = label,
            color = if (isPressed) mainColor else Color.LightGray,
            fontWeight = if (isPressed) FontWeight.Bold else FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
        )
    }
}

@Preview(name = "Phone", device = "spec:width=360dp,height=800dp")
@Preview(name = "Phone_flip", device = "spec:width=360dp,height=880dp")
@Preview(name = "Tablet", device = "spec:width=1280dp,height=800dp")
@Preview(name = "Foldable", device = "spec:width=673dp,height=841dp")
@Composable
private fun MediaTypeChipsPreview() {
    Row() {
        ApodActionButtons("저장",  {})
        ApodActionButtons("공유",  {})
    }
}