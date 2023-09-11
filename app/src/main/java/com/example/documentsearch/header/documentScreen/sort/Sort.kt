package com.example.documentsearch.header.documentScreen.sort

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentsearch.R
import com.example.documentsearch.ui.theme.TextColor

/**
 * Функция отображает кнопку сортировки(не активная)
 */
@Composable
fun Sort() {
    Box {
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            Icon(
                painter = painterResource(activeSortElement.value.icon),
                contentDescription = "Сортировать по",
                modifier = Modifier.size(24.dp),
                tint = TextColor,
            )
            Text(
                text = "Сортировать по",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                    fontWeight = FontWeight(600),
                    color = TextColor,
                ),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}