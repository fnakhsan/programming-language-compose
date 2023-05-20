package com.example.programminglanguagecompose.ui.screen.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.programminglanguagecompose.R
import com.example.programminglanguagecompose.ui.theme.ProgrammingLanguageComposeTheme
import com.example.programminglanguagecompose.ui.values.spacingSmaller

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        val listState = rememberLazyListState()
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
//            groupedHeroes.forEach { (initial, heroes) ->
//                stickyHeader {
//                    CharacterHeader(initial)
//                }
//                items(heroes, key = { it.id }) { hero ->
//                    HeroListItem(
//                        name = hero.name,
//                        photoUrl = hero.photoUrl,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .animateItemPlacement(tween(durationMillis = 100))
//                    )
//                }
//            }
        }
    }
}

@Composable
fun ProgrammingLanguageItems(
    name: String,
    photo: Int,
    desc: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable {}
    ) {
        AsyncImage(
            model = photo,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(spacingSmaller)
                .size(60.dp)
                .clip(CircleShape)
        )
        Column(
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = spacingSmaller),
            )
            Text(
                text = desc,
                fontWeight = FontWeight.Normal,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = spacingSmaller),
            )
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
        name = "Kotlin",
        photo = R.drawable.kotlin,
        desc = "Kotlin is a cross-platform, statically typed, general-purpose programming language with type inference. Kotlin is designed to interoperate fully with Java, and the JVM version of Kotlin's standard library depends on the Java Class Library, but type inference allows its syntax to be more concise. Kotlin mainly targets the JVM, but also compiles to JavaScript (e.g., for frontend web applications using React) or native code (via LLVM); e.g., for native iOS apps sharing business logic with Android apps. Language development costs are borne by JetBrains, while the Kotlin Foundation protects the Kotlin trademark. On 7 May 2019, Google announced that the Kotlin programming language is now its preferred language for Android app developers. Since the release of Android Studio 3.0 in October 2017, Kotlin has been included as an alternative to the standard Java compiler."
    )
}