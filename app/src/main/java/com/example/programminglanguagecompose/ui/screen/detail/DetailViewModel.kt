package com.example.programminglanguagecompose.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programminglanguagecompose.data.Repository
import com.example.programminglanguagecompose.data.Resource
import com.example.programminglanguagecompose.data.model.Language
import com.example.programminglanguagecompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository): ViewModel() {
    private val _detailUiState =
        MutableStateFlow<UiState<Language>>(UiState.Initial)
    val detailUiState = _detailUiState.asStateFlow()

    fun getLanguageDetails(name: String) {
        viewModelScope.launch {
            repository.getLanguageDetails(name = name).collect { resource ->
                when (resource) {
                    Resource.Loading -> _detailUiState.emit(UiState.Loading(true))
                    is Resource.Success -> {
                        _detailUiState.emit(UiState.Loading(false))
                        _detailUiState.emit(UiState.Success(resource.data))
                    }
                    is Resource.Error -> {
                        _detailUiState.emit(UiState.Loading(false))
                        _detailUiState.emit(UiState.Error(resource.error))
                    }
                }
            }
        }
    }
}