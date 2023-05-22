package com.example.programminglanguagecompose.ui.screen.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.programminglanguagecompose.R
import com.example.programminglanguagecompose.ui.values.spacingLarge
import com.example.programminglanguagecompose.ui.values.spacingLarger
import com.example.programminglanguagecompose.ui.values.spacingTiny
import com.example.programminglanguagecompose.ui.values.textLarger

@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val emailAddress = stringResource(id = R.string.email)
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(spacingLarger)
    ) {
        Card(
            shape = CircleShape,
            elevation = 2.dp,
            modifier = Modifier.height(350.dp).fillMaxWidth()
        ) {
            Image(
                painterResource(R.drawable.profile),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.padding(bottom = spacingLarge))
        Text(
            text = stringResource(id = R.string.username).toUpperCase(Locale.current),
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(bottom = spacingTiny))
        Text(
            text = emailAddress,
            fontSize = textLarger,
            textAlign = TextAlign.Center,
            color = Color.Blue,
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:$emailAddress")
                }
                context.startActivity(intent)
            }.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}