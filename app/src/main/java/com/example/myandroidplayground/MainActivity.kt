package com.example.myandroidplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myandroidplayground.ui.theme.MyAndroidPlaygroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAndroidPlaygroundTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavHost(
                        modifier = Modifier.padding(innerPadding)   // 여기 한 번만
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
fun GreetingPreview() {
    MyAndroidPlaygroundTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AppNavHost(
                modifier = Modifier.padding(innerPadding)   // 여기 한 번만
            )
        }
    }
}