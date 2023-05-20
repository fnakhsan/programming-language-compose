package com.example.programminglanguagecompose.ui.screen.home

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.programminglanguagecompose.R
import com.example.programminglanguagecompose.data.model.Language
import com.example.programminglanguagecompose.data.model.LanguagesData
import com.example.programminglanguagecompose.ui.common.UiState
import com.example.programminglanguagecompose.ui.theme.ProgrammingLanguageComposeTheme
import com.example.programminglanguagecompose.ui.values.spacingRegular
import com.example.programminglanguagecompose.ui.values.textLarge
import com.example.programminglanguagecompose.ui.values.textRegular
import com.example.programminglanguagecompose.utils.UiText.Companion.asString
import com.example.programminglanguagecompose.utils.ViewModelFactory

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
) {
    val listDataState = homeViewModel.listProgrammingLanguageState.collectAsState()
    val query by homeViewModel.query
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar(
            query = query,
            onQueryChange = homeViewModel::searchListProgrammingLanguages,
            modifier = Modifier.background(MaterialTheme.colors.primary)
        )
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = modifier.fillMaxSize()
        ) {
            val listState = rememberLazyListState()
            listDataState.value.let { state ->
                when (state) {
                    UiState.Initial -> homeViewModel.getListProgrammingLanguages()
                    is UiState.Loading -> CircularProgressIndicator()
                    is UiState.Empty -> HomeScreenEmptyContent()
                    is UiState.Success -> HomeScreenContent(listState, state.data,)
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

@Composable
fun HomeScreenEmptyContent() {

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenContent(
    listState: LazyListState,
    data: List<Language>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
//        IDK why we must put this item{} code
//        (if you delete it then the list will not automatically scroll to the top,
//        when the user delete the search query)
        item {

        }
        items(data, key = { it.name }) { language ->
            ProgrammingLanguageItems(
                language,
                modifier = modifier
                    .fillMaxWidth()
                    .animateItemPlacement(tween(durationMillis = 100))
            )
        }
    }
}

@Composable
fun ProgrammingLanguageItems(
    language: Language,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable {}
            .padding(spacingRegular)
    ) {
        language.apply {
            AsyncImage(
                model = photo,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(end = spacingRegular)
                    .size(54.dp)
            )
            Column(
                verticalArrangement = Arrangement.SpaceAround,
            ) {
                Text(
                    text = name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = textLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = detail,
                    fontWeight = FontWeight.Normal,
                    fontSize = textRegular,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ProgrammingLanguageComposeTheme {
        HomeScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ProgrammingLanguageItemsPreview() {
    ProgrammingLanguageItems(
        language = LanguagesData.listData[0]
    )
}