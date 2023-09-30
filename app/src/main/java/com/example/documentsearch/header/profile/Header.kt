package com.example.documentsearch.header.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.documentsearch.R
import com.example.documentsearch.ui.theme.TextColor


/**
 * Функция отображает header страницы
 */
@Composable
fun Header() {
    Box(modifier = Modifier.zIndex(3f)) {
        Image(
            painter = painterResource(R.drawable.header_profile),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
        )
        Text(
            text = "Профиль",
            style = TextStyle(
                fontSize = 19.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                fontWeight = FontWeight(600),
                color = TextColor,
            ),
            modifier = Modifier.padding(start = 20.dp, top = 45.dp)
        )

    }
}