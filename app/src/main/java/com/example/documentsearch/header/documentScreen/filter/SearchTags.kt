package com.example.documentsearch.header.documentScreen.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.documentsearch.ui.theme.TextColor
import kotlinx.coroutines.launch

var searchTagsValue = mutableStateOf(TextFieldValue(""))


/**
 * Функция отображает поиск тегов из списка тегов в фильтре
 */
@Composable
fun SearchTags() {
    var isFocused by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    BasicTextField(
        value = searchTagsValue.value,
        onValueChange = { newTextFieldValue ->
            searchTagsValue.value = newTextFieldValue
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 1.dp)
            .height(40.dp)
            .border(1.dp, TextColor, RoundedCornerShape(10.dp))
            .background(color = Color.Transparent)
            .onFocusChanged { isFocused = it.isFocused },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        textStyle = TextStyle(color = TextColor),
        decorationBox = { innerTextField ->
            LaunchedEffect(isFocused) {
                if (!isFocused) {
                    // Проверяем, является ли курсор в конце текста
                    val isCursorAtEnd = searchTagsValue.value.selection.collapsed && searchTagsValue.value.selection.start == searchTagsValue.value.text.length
                    if (!isCursorAtEnd) {
                        // Прокручиваем курсор в конец
                        coroutineScope.launch {
                            val newSelection = TextRange(searchTagsValue.value.text.length)
                            searchTagsValue.value = searchTagsValue.value.copy(selection = newSelection)
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp, vertical = 1.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = TextColor,
                    modifier = Modifier.padding(end = 5.dp).size(20.dp)
                )
                innerTextField()
            }
        },
        cursorBrush = SolidColor(TextColor)
    )
}