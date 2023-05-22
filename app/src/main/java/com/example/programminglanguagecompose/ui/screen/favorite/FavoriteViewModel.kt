package com.example.programminglanguagecompose.ui.screen.favorite

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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

class FavoriteViewModel(private val repository: Repository) : ViewModel() {
    private val _listFavLanguageState =
        MutableStateFlow<UiState<List<Language>>>(UiState.Initial)
    val listFavLanguageState = _listFavLanguageState.asStateFlow()

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun getFavLanguages() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavoriteLanguages().collect { resource ->
                when (resource) {
                    Resource.Loading -> _listFavLanguageState.emit(UiState.Loading(true))
                    is Resource.Success -> {
                        _listFavLanguageState.emit(UiState.Loading(false))
                        resource.data.collect {
                            if (it.isEmpty()) {
                                _listFavLanguageState.emit(UiState.Empty)
                            } else {
                                _listFavLanguageState.emit(
                                    UiState.Success(it)
                                )
                            }
                        }
                    }
                    is Resource.Error -> {
                        _listFavLanguageState.emit(UiState.Loading(false))
                        _listFavLanguageState.emit(UiState.Error(resource.error))
                    }
                }
            }
        }
    }

    fun searchFavLanguages(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch(Dispatchers.IO) {
            if (newQuery.isNotBlank() && newQuery.isNotEmpty()) {
                repository.searchFavoriteLanguages(newQuery).collect { resource ->
                    when (resource) {
                        Resource.Loading -> _listFavLanguageState.emit(UiState.Loading(true))
                        is Resource.Success -> {
                            _listFavLanguageState.emit(UiState.Loading(false))
                            resource.data.collect {
                                _listFavLanguageState.emit(UiState.Success(it))
                            }
                        }
                        is Resource.Error -> {
                            _listFavLanguageState.emit(UiState.Loading(false))
                            _listFavLanguageState.emit(UiState.Error(resource.error))
                        }
                    }
                }
            } else getFavLanguages()
        }
    }
}