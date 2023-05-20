package com.example.programminglanguagecompose.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programminglanguagecompose.data.Repository
import com.example.programminglanguagecompose.data.Resource
import com.example.programminglanguagecompose.data.model.Language
import com.example.programminglanguagecompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {
    private val _listProgrammingLanguageState =
        MutableStateFlow<UiState<List<Language>>>(UiState.Initial)
    val listProgrammingLanguageState = _listProgrammingLanguageState.asStateFlow()

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun getListProgrammingLanguages() {
        viewModelScope.launch {
            repository.getLanguages().collect { resource ->
                when (resource) {
                    Resource.Loading -> _listProgrammingLanguageState.emit(UiState.Loading(true))
                    is Resource.Success -> {
                        _listProgrammingLanguageState.emit(UiState.Loading(false))
                        _listProgrammingLanguageState.emit(UiState.Success(resource.data))
                    }
                    is Resource.Error -> {
                        _listProgrammingLanguageState.emit(UiState.Loading(false))
                        _listProgrammingLanguageState.emit(UiState.Error(resource.error))
                    }
                }
            }
        }
    }

    fun searchListProgrammingLanguages(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch {
            repository.searchLanguages(newQuery).collect { resource ->
                when (resource) {
                    Resource.Loading -> _listProgrammingLanguageState.emit(UiState.Loading(true))
                    is Resource.Success -> {
                        _listProgrammingLanguageState.emit(UiState.Loading(false))
                        _listProgrammingLanguageState.emit(
                            if (resource.data.isNotEmpty()) UiState.Success(resource.data) else UiState.Empty
                        )
                    }
                    is Resource.Error -> {
                        _listProgrammingLanguageState.emit(UiState.Loading(false))
                        _listProgrammingLanguageState.emit(UiState.Error(resource.error))
                    }
                }
            }
        }
    }
}