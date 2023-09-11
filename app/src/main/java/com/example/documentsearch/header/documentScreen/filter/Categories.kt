package com.example.documentsearch.header.documentScreen.filter

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentsearch.R
import com.example.documentsearch.ui.theme.TextColor

/**
 * Функция отображает категории при поиске в фильтре
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Categories() {
    val context = LocalContext.current
    val categories = arrayOf("Научная статья", "Курсовая работа", "Дипломная работа")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(categories[0]) }

    MaterialTheme(
        colors = MaterialTheme.colors.copy(surface = TextColor),
        shapes = MaterialTheme.shapes.copy(
            medium = RoundedCornerShape(10.dp)
        ),
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            BasicTextField(
                value = "",
                onValueChange = {},
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                    fontWeight = FontWeight(500),
                    color = TextColor,
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {}
                    .background(Color.Transparent)
                    .border(1.dp, TextColor, shape = RoundedCornerShape(10.dp))
                    .padding(8.dp),
                enabled = false
            ) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = selectedText, style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                            fontWeight = FontWeight(500),
                            color = TextColor,
                            textAlign = TextAlign.Center,
                        )
                    )
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = TextColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

            }
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(horizontal = 10.dp)
            ) {
                categories.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}