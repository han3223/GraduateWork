package com.example.documentsearch.screens.document

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.documentsearch.R
import com.example.documentsearch.ui.theme.ORDINARY_TEXT
import com.example.documentsearch.ui.theme.TextColor

class Categories {
    private val categories = arrayOf("Научная статья", "Курсовая работа", "Дипломная работа")

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun DropDownContainer(onCategoryChange: (String) -> Unit) {
        var isExpanded by remember { mutableStateOf(false) }
        var selectedCategory by remember { mutableStateOf(categories[0]) }

        MaterialTheme(
            colors = MaterialTheme.colors.copy(surface = TextColor),
            shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(10.dp)),
        ) {
            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = { isExpanded = !isExpanded },
            ) {
                CategoryTextField(selectedCategory)
                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false },
                    modifier = Modifier
                        .background(Color.Transparent)
                        .padding(start = 10.dp)
                ) {
                    categories.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                selectedCategory = item
                                onCategoryChange(item)
                                isExpanded = false
                            }
                        )
                    }
                }
            }
        }
    }


    @Composable
    private fun CategoryTextField(selectedCategory: String) {
        val containerModifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .border(1.dp, TextColor, shape = RoundedCornerShape(10.dp))
            .padding(8.dp)

        BasicTextField(
            value = "",
            onValueChange = {},
            modifier = containerModifier,
            enabled = false
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
                ) {
                Icon(
                    painter = painterResource(R.drawable.pdf_add_white), //TODO(Поменять заглушку)
                    contentDescription = "Добавить статью",
                    modifier = Modifier.size(25.dp),
                    tint = TextColor,
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = selectedCategory, style = ORDINARY_TEXT, modifier = Modifier.weight(1f))
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = TextColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}