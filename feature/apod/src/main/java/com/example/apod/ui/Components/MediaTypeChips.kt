package com.example.apod.ui.Components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apod.ui.theme.mainColor

@Composable
fun MediaTypeChips(
    typeInfo: String,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .border(0.5.dp, mainColor, RoundedCornerShape(45))
            .padding(8.dp)
    ) {
        Text(
            text = typeInfo,
            color = mainColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(name = "Phone", device = "spec:width=360dp,height=800dp")
@Preview(name = "Phone_flip", device = "spec:width=360dp,height=880dp")
@Preview(name = "Tablet", device = "spec:width=1280dp,height=800dp")
@Preview(name = "Foldable", device = "spec:width=673dp,height=841dp")
@Composable
private fun MediaTypeChipsPreview() {
    MediaTypeChips("image")
}