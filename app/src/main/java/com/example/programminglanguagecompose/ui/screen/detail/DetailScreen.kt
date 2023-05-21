package com.example.programminglanguagecompose.ui.screen.detail

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.programminglanguagecompose.ui.screen.home.HomeScreenEmptyContent
import com.example.programminglanguagecompose.ui.values.spacingLarge
import com.example.programminglanguagecompose.ui.values.spacingRegular
import com.example.programminglanguagecompose.utils.UiText.Companion.asString
import com.example.programminglanguagecompose.utils.ViewModelFactory

@Composable
fun DetailScreen(
    name: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    detailViewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
) {
    val dataState = detailViewModel.detailUiState.collectAsState()
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
        }
    ) {
        dataState.value.let { state ->
            when (state) {
                UiState.Initial -> detailViewModel.getLanguageDetails(name)
                is UiState.Loading -> CircularProgressIndicator()
                is UiState.Empty -> HomeScreenEmptyContent()
                is UiState.Success -> DetailScreenContent(state.data, it)
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
    paddingValues: PaddingValues,
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


//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedCrossfadeTargetStateParameter")
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DetailScreen(
//    navHostController: NavHostController,
//    animeID: Int,
//    anime: Resource<Anime>,
//    initiate: (Int) -> Unit,
//    updateAnimeByAnimeID: (Int) -> Unit
//) {
//    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
//    val scrollState = rememberScrollState()
//
//    var isDataInitiated by remember {
//        mutableStateOf(false)
//    }
//    if (!isDataInitiated) {
//        initiate(animeID)
//        isDataInitiated = true
//    }
//    val snackbarHostState = remember {
//        SnackbarHostState()
//    }
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                modifier = Modifier,
//                title = {
//                    Text(
//                        text = "Detail Anime",
//                        style = MaterialTheme.typography.titleMedium,
//                    )
//                },
//                navigationIcon = {
//                    IconButton(
//                        onClick = {
//                            navHostController.popBackStack()
//                        }) {
//                        Icon(
//                            imageVector = Icons.Filled.ArrowBack,
//                            contentDescription = "Navigation Icon",
//                        )
//                    }
//                },
//                colors = TopAppBarDefaults.smallTopAppBarColors(
//                    scrolledContainerColor = MaterialTheme.colorScheme.background,
//                    containerColor = Color.Transparent,
//                    titleContentColor =
//                    if (scrollState.value == 0) {
//                        Color.White
//                    } else {
//                        if (isSystemInDarkTheme()) Color.White
//                        else Color.Black
//                    },
//                    navigationIconContentColor =
//                    if (scrollState.value == 0) {
//                        Color.White
//                    } else {
//                        if (isSystemInDarkTheme()) Color.White
//                        else Color.Black
//                    }
//                ),
//                scrollBehavior = scrollBehavior
//            )
//        },
//        floatingActionButton = {
//            when (anime) {
//                is Resource.Success -> {
//                    FloatingActionButton(
//                        modifier = Modifier
//                            .padding(16.dp),
//                        onClick = {
//                            updateAnimeByAnimeID(animeID)
//                        },
//                        containerColor = MaterialTheme.colorScheme.primary
//                    ) {
//                        Crossfade(targetState = anime.data?.isFavorite, label = "") {
//                            Icon(
//                                imageVector =
//                                if (anime.data?.isFavorite as Boolean) {
//                                    Icons.Default.Favorite
//                                } else {
//                                    Icons.Default.FavoriteBorder
//                                },
//                                tint = Color.White,
//                                contentDescription = "Favorite Button"
//                            )
//                        }
//                    }
//                }
//
//                else -> Unit
//            }
//        },
//        snackbarHost = {
//            SnackbarHost(snackbarHostState)
//
//        }
//}
//
//else -> Unit
//}
//}
//}