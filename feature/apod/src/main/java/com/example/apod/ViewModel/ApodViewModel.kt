package com.example.apod.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apod.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ApodViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<ApodUiState>(ApodUiState.Loading)
    val uiState: StateFlow<ApodUiState> = _uiState.asStateFlow()

    init {

        val endDate = LocalDate.now()
        val startDate = endDate.minusDays(6)   // 최근 7일 (오늘 포함)
        fetchApodByDateRange(
            start = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
            end = endDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
        )

    }

    private fun fetchApodByDateRange(start: String, end: String) {

        viewModelScope.launch {

            _uiState.value = ApodUiState.Loading

            try {
                val result = RetrofitInstance.api.getApodByDateRange(start,end)
                _uiState.value = ApodUiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = ApodUiState.Error(e.message ?: "알 수 없는 오류")
            }
        }
    }
}