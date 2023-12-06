package com.example.documentsearch.screens.document

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentsearch.R
import com.example.documentsearch.ui.theme.HIGHLIGHTING_BOLD_TEXT
import com.example.documentsearch.ui.theme.ORDINARY_TEXT
import com.example.documentsearch.ui.theme.TextColor
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog
import java.time.LocalDate

class Dates {
    @Composable
    fun ContainerWithDates(
        onDateFromChange: (LocalDate) -> Unit,
        onDateBeforeChange: (LocalDate) -> Unit
    ) {
        val mainContainerModifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .padding(0.dp, 0.dp, 0.dp, 15.dp)

        Row(
            modifier = mainContainerModifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "от ", style = HIGHLIGHTING_BOLD_TEXT)
            Box(modifier = Modifier.weight(0.45f)) {
                DateTextField { onDateFromChange(it) }
            }
            Text(text = " - ", style = HIGHLIGHTING_BOLD_TEXT)
            Text(text = "до ", style = HIGHLIGHTING_BOLD_TEXT)
            Box(modifier = Modifier.weight(0.45f)) {
                DateTextField { onDateBeforeChange(it) }
            }
        }

    }

    @Composable
    private fun DateTextField(onDateChange: (LocalDate) -> Unit) {
        var selectedDate by remember { mutableStateOf(LocalDate.now()) }
        var isClicked by remember { mutableStateOf(false) }
        if (isClicked) {
            SelectDate(
                onDateChange = {
                    selectedDate = it
                    isClicked = false
                    onDateChange(it)
                }
            )
        }

        Row(modifier = Modifier) {
            BasicTextField(
                value = selectedDate.toString(),
                onValueChange = {},
                textStyle = ORDINARY_TEXT,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.45f)
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            isClicked = true
                        })
                    }
                    .border(1.dp, TextColor, shape = RoundedCornerShape(10.dp))
                    .padding(8.dp),
                enabled = false
            )
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun SelectDate(onDateChange: (LocalDate) -> Unit) {
        DatePickerDialog(
            onDismissRequest = {},
            onDateChange = { onDateChange(it) },
            initialDate = LocalDate.now(),
            today = LocalDate.now(),
            showDaysAbbreviations = true,
            highlightToday = true,
            title = { Text("Выберите дату") },
        )
    }
}