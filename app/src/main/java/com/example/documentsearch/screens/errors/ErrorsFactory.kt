package com.example.documentsearch.screens.errors

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentsearch.R
import com.example.documentsearch.ui.theme.SelectedColor

data class ErrorsFactory(
    val image: Int,
    val titleError: String,
    val descriptionError: String? = null,
    val titleButton: String? = null
) {
    @Composable
    fun Content() {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 30.dp)
                .widthIn(200.dp, 300.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item { ImageError() }
            item { ErrorTitle() }
            item { DescriptionError() }
            item { HelpButton() }
        }
    }

    @Composable
    private fun ImageError() {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = image),
            contentDescription = null
        )
    }

    @Composable
    private fun ErrorTitle() {
        Text(
            text = titleError,
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 19.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                fontWeight = FontWeight(600),
                textAlign = TextAlign.Center,
                color = Color.Black,
            )
        )
    }

    @Composable
    private fun DescriptionError() {
        if (descriptionError != null) {
            Text(
                text = descriptionError,
                style = TextStyle(
                    fontSize = 17.sp,
                    fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                    fontWeight = FontWeight(600),
                    textAlign = TextAlign.Center,
                    color = SelectedColor,
                )
            )
        }
    }

    @Composable
    private fun HelpButton() {

    }
}