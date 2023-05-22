package com.example.programminglanguagecompose.ui.screen.detail

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.programminglanguagecompose.R
import com.example.programminglanguagecompose.data.model.Language
import com.example.programminglanguagecompose.ui.common.UiState
import com.example.programminglanguagecompose.ui.components.EmptyContentScreen
import com.example.programminglanguagecompose.ui.values.spacingLarge
import com.example.programminglanguagecompose.ui.values.spacingRegular
import com.example.programminglanguagecompose.utils.UiText.Companion.asString
import com.example.programminglanguagecompose.utils.ViewModelFactory

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    id: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    detailViewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
) {
    val dataState = detailViewModel.detailUiState.collectAsState()
    val favState = detailViewModel.favState.collectAsState()
    var isFav by rememberSaveable { mutableStateOf(false) }
    var language by rememberSaveable { mutableStateOf<Language?>(null) }
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = modifier,
                title = {
                    Text(
                        text = stringResource(R.string.text_detail)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigateBack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back_hint),
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(spacingRegular),
                onClick = {
                    detailViewModel.apply {
                        language?.let { if (isFav) removeFromFavorite(it) else addedToFavorite(it) }
                    }
                }
            ) {
                favState.value.let { state ->
                    when (state) {
                        UiState.Initial -> detailViewModel.isFavorite(id)
                        is UiState.Loading -> CircularProgressIndicator()
                        is UiState.Empty -> {}
                        is UiState.Success -> {
                            isFav = state.data
                            Icon(
                                imageVector =
                                if (state.data) {
                                    Icons.Default.Favorite
                                } else {
                                    Icons.TwoTone.Favorite
                                },
                                tint = Color.White,
                                contentDescription = if (state.data) "Favored" else "Not Favored"
                            )
                        }
                        is UiState.Error -> Toast.makeText(
                            LocalContext.current,
                            state.error.asString(LocalContext.current),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    ) {
        dataState.value.let { state ->
            when (state) {
                UiState.Initial -> detailViewModel.getLanguageDetails(id)
                is UiState.Loading -> CircularProgressIndicator()
                is UiState.Empty -> EmptyContentScreen(id = R.string.empty_detail, modifier = modifier )
                is UiState.Success -> {
                    language = state.data
                    DetailScreenContent(state.data)
                }
                is UiState.Error -> Toast.makeText(
                    LocalContext.current,
                    state.error.asString(LocalContext.current),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

@Composable
fun DetailScreenContent(
    language: Language,
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(spacingRegular)
    ) {
        language.apply {
            AsyncImage(
                model = photo,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = spacingRegular)
            )
            Text(
                text = name,
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(bottom = spacingRegular)
                    .fillMaxWidth()
            )
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(bottom = spacingRegular)
            ) {
                Text(
                    text = stringResource(R.string.developer),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(end = spacingRegular)
                )
                Text(
                    text = developer,
                    modifier = Modifier
                        .weight(1f)
                )
            }
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(bottom = spacingLarge)
            ) {
                Text(
                    text = stringResource(R.string.paradigm),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(end = spacingRegular)
                )
                Text(
                    text = paradigm,
                    modifier = Modifier
                        .weight(1f)
                )
            }
            Text(
                text = detail,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(bottom = spacingRegular)
            )
        }
    }
}