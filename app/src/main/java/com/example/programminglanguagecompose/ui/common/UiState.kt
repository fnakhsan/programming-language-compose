package com.example.programminglanguagecompose.ui.common

import com.example.programminglanguagecompose.utils.UiText

sealed class UiState<out T: Any?> {
    object Initial : UiState<Nothing>()
    object Empty : UiState<Nothing>()
    data class Loading(val isLoading: Boolean) : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val error: UiText) : UiState<Nothing>()
}
