package com.example.programminglanguagecompose.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.programminglanguagecompose.data.model.Language
import com.example.programminglanguagecompose.ui.values.spacingRegular
import com.example.programminglanguagecompose.ui.values.textLarge
import com.example.programminglanguagecompose.ui.values.textRegular
import com.example.programminglanguagecompose.utils.Tag

@Composable
fun ProgrammingLanguageItems(
    language: Language,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
) {
    language.apply {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .clickable {
                    navigateToDetail(id)
                    Log.d(Tag.repository, "Click: $id")
                }
                .padding(spacingRegular)
        ) {
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