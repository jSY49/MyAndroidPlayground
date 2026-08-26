package com.example.apod.ui.Screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.w3c.dom.Text

@Composable
fun AsteroidScreen() {
    Text(text = "asteroid")
}

@Preview(name = "Phone", device = "spec:width=360dp,height=800dp")
@Preview(name = "Phone_flip", device = "spec:width=360dp,height=880dp")
@Preview(name = "Tablet", device = "spec:width=1280dp,height=800dp")
@Preview(name = "Foldable", device = "spec:width=673dp,height=841dp")
@Composable
fun AsteroidScreenPreview() {
}