package com.example.programminglanguagecompose.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.programminglanguagecompose.data.model.Language
import com.example.programminglanguagecompose.utils.Const
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LanguageListContentScreen(
    listState: LazyListState,
    data: List<Language>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
) {
    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 2 }
        }
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 80.dp),
            modifier = modifier.testTag(Const.tagTestList)
        ) {
            item {
//            IDK why we must put this item {} code
//            (if you delete it then the list will not automatically scroll to the top,
//            when the user delete the search query)
            }
            items(data, key = { it.name }) { language ->
                ProgrammingLanguageItems(
                    language,
                    modifier = modifier
                        .fillMaxWidth()
                        .animateItemPlacement(tween(durationMillis = 100)),
                    navigateToDetail = navigateToDetail
                )
            }
        }
        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(
                onClick = {
                    scope.launch {
                        listState.scrollToItem(index = 0)
                    }
                }
            )
        }
    }
}

@Composable
fun ScrollToTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .shadow(elevation = 10.dp, shape = CircleShape)
            .clip(shape = CircleShape)
            .size(56.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = MaterialTheme.colors.primary
        )
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = null,
        )
    }
}