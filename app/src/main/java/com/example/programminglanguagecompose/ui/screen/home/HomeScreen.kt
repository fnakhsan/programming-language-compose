package com.example.programminglanguagecompose.ui.screen.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.programminglanguagecompose.R
import com.example.programminglanguagecompose.ui.common.UiState
import com.example.programminglanguagecompose.ui.components.EmptyContentScreen
import com.example.programminglanguagecompose.ui.components.LanguageListContentScreen
import com.example.programminglanguagecompose.ui.values.spacingRegular
import com.example.programminglanguagecompose.utils.UiText.Companion.asString
import com.example.programminglanguagecompose.utils.ViewModelFactory

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateToDetail: (Int) -> Unit,
) {
    val listDataState = homeViewModel.listProgrammingLanguageState.collectAsState()
    val query by homeViewModel.query
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar(
            query = query,
            onQueryChange = homeViewModel::searchLanguages,
            modifier = Modifier.background(MaterialTheme.colors.primary)
        )
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = modifier.fillMaxSize()
        ) {
            val listState = rememberLazyListState()
            listDataState.value.let { state ->
                when (state) {
                    UiState.Initial -> homeViewModel.getLanguages()
                    is UiState.Loading -> CircularProgressIndicator()
                    is UiState.Empty -> EmptyContentScreen(R.string.empty_lang, modifier)
                    is UiState.Success -> LanguageListContentScreen(
                        listState,
                        state.data,
                        navigateToDetail = navigateToDetail
                    )
                    is UiState.Error -> Toast.makeText(
                        LocalContext.current,
                        state.error.asString(LocalContext.current),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        placeholder = {
            Text(stringResource(R.string.search_hint))
        },
        modifier = modifier
            .padding(spacingRegular)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .clip(RoundedCornerShape(spacingRegular))
    )
}