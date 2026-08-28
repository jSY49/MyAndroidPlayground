package com.example.apod.ViewModel

import com.example.apod.data.ApodResponse

sealed interface ApodUiState {
    object Loading : ApodUiState
    data class Success(val data: List<ApodResponse>) : ApodUiState
    data class Error(val message: String) : ApodUiState
}

