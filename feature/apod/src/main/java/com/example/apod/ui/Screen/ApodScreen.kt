package com.example.apod.ui.Screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apod.ViewModel.ApodUiState
import com.example.apod.ViewModel.ApodViewModel

@Composable
fun ApodScreen(modifier: Modifier = Modifier,viewModel: ApodViewModel = viewModel()) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Surface(
        modifier = modifier.fillMaxSize()
    ) {

   //데이터 파싱 확인 용
   Box(Modifier.fillMaxSize(), Alignment.Center) {
        when (val state = uiState) {
            is ApodUiState.Loading -> CircularProgressIndicator()
            is ApodUiState.Error -> Text("에러: ${state.message}")
            is ApodUiState.Success -> LazyColumn {
                items(state.data) { item ->
                    Column {
                        Text(item.title, style = MaterialTheme.typography.titleLarge)
                        Text(item.explanation)
                    }
                }
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
fun ApodScreenPreview() {
    ApodScreen()
}