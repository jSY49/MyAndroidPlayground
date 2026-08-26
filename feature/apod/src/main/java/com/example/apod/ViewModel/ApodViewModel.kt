package com.example.apod.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apod.BuildConfig
import com.example.apod.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ApodViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<ApodUiState>(ApodUiState.Loading)
    val uiState : StateFlow<ApodUiState> = _uiState.asStateFlow()

    init{
        fetchApod()
    }

    private fun fetchApod() {

        viewModelScope.launch {

            _uiState.value = ApodUiState.Loading

            try {
                android.util.Log.d("ApodVM", "요청 시작")
                val result = RetrofitInstance.api.getApod(apiKey = BuildConfig.NASA_API_KEY)
                android.util.Log.d("ApodVM", "성공: $result")
                _uiState.value = ApodUiState.Success(result)
            } catch (e: Exception) {
                android.util.Log.e("ApodVM", "실패", e)
                _uiState.value = ApodUiState.Error(e.message ?: "알 수 없는 오류")
            }
        }
    }
}