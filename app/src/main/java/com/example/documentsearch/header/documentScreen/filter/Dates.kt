package com.example.documentsearch.header.documentScreen.filter

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentsearch.R
import com.example.documentsearch.ui.theme.TextColor
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog
import java.time.LocalDate

var datePickerFrom = mutableStateOf(false)
var dateFrom = mutableStateOf(LocalDate.now())
var datePickerBefore = mutableStateOf(false)
var dateBefore = mutableStateOf(LocalDate.now())

/**
 * Функция отображает даты в фильтре
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Dates() {
    if (datePickerFrom.value) {
        DatePickerDialog(
            onDismissRequest = {},
            onDateChange = {
                datePickerFrom.value = false
                dateFrom.value = it
            },
            initialDate = dateFrom.value,
            today = LocalDate.now(),
            showDaysAbbreviations = true,
            highlightToday = true,
            title = { Text("Выберите дату") },
        )
    }
    if (datePickerBefore.value) {
        DatePickerDialog(
            onDismissRequest = {},
            onDateChange = {
                datePickerBefore.value = false
                dateBefore.value = it
            },
            initialDate = dateFrom.value,
            today = LocalDate.now(),
            showDaysAbbreviations = true,
            highlightToday = true,
            title = { Text("Выберите дату") },
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .padding(0.dp, 0.dp, 0.dp, 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "от ",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                fontWeight = FontWeight(600),
                color = TextColor,
            ),
        )
        BasicTextField(
            value = dateFrom.value.toString(),
            onValueChange = {},
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                fontWeight = FontWeight(500),
                color = TextColor,
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.45f)
                .clickable { datePickerFrom.value = true }
                .border(1.dp, TextColor, shape = RoundedCornerShape(10.dp)).padding(8.dp),
            enabled = false
        )
        Text(
            text = "  -  ",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                fontWeight = FontWeight(600),
                color = TextColor,
            ),
        )
        Text(
            text = "до ",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                fontWeight = FontWeight(600),
                color = TextColor,
            ),
        )
        BasicTextField(
            value = dateBefore.value.toString(),
            onValueChange = {},
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                fontWeight = FontWeight(500),
                color = TextColor,
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.45f)
                .clickable { datePickerBefore.value = true }
                .border(1.dp, TextColor, shape = RoundedCornerShape(10.dp)).padding(8.dp),
            enabled = false
        )
    }
}