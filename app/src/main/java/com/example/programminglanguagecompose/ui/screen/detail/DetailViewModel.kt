package com.example.programminglanguagecompose.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programminglanguagecompose.data.Repository
import com.example.programminglanguagecompose.data.Resource
import com.example.programminglanguagecompose.data.model.Language
import com.example.programminglanguagecompose.ui.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository) : ViewModel() {
    private val _detailUiState =
        MutableStateFlow<UiState<Language>>(UiState.Initial)
    val detailUiState = _detailUiState.asStateFlow()

    private val _favState =
        MutableStateFlow<UiState<Boolean>>(UiState.Initial)
    val favState = _favState.asStateFlow()

    fun getLanguageDetails(id: Int) {
        viewModelScope.launch {
            repository.getLanguageDetails(id = id).collect { resource ->
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

    fun isFavorite(id: Int) {
        viewModelScope.launch {
            repository.isFavorite(id = id).collect { resource ->
                when (resource) {
                    Resource.Loading -> _favState.emit(UiState.Loading(true))
                    is Resource.Success -> {
                        _favState.emit(UiState.Loading(false))
                        _favState.emit(UiState.Success(resource.data))
                    }
                    is Resource.Error -> {
                        _favState.emit(UiState.Loading(false))
                        _favState.emit(UiState.Error(resource.error))
                    }
                }
            }
        }
    }

    fun addedToFavorite(language: Language) = viewModelScope.launch(Dispatchers.IO) {
        repository.addedToFavorite(language)
        _favState.emit(UiState.Success(data = true))
    }

    fun removeFromFavorite(language: Language) = viewModelScope.launch(Dispatchers.IO) {
        repository.removeFromFavorite(language)
        _favState.emit(UiState.Success(data = false))
    }
}