package com.example.documentsearch.header.documentScreen

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.documentsearch.R
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.TextColor
import kotlinx.coroutines.launch

// TODO(Загрузка файла сделана, теперь мне надо сделать запрос прав если их нет)

/**
 * Функция отображает элементы для header на странице выбора документа
 */
@SuppressLint("RememberReturnType")
@Composable
fun SearchDocument() {
    val text = remember { mutableStateOf(TextFieldValue("")) } // Текст в текстовом поле
    var iconDocument by remember { mutableIntStateOf(R.drawable.pdf_add_white) } // Иконка документа(добавить статью)
    var isFocused by remember { mutableStateOf(false) } // Проверка фокуса
    val coroutineScope = rememberCoroutineScope() // Корутины

    // Делает запрос в файлы телефона
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { _ ->
                iconDocument = R.drawable.pdf_added_white
            }
        }

    Row(
        modifier = Modifier
            .padding(20.dp, 0.dp, 20.dp, 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            // Текстовое поле с запросом
            BasicTextField(
                value = text.value,
                onValueChange = { newTextFieldValue ->
                    text.value = newTextFieldValue
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 1.dp)
                    .height(40.dp)
                    .background(color = MainColor)
                    .onFocusChanged { isFocused = it.isFocused },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                textStyle = TextStyle(color = TextColor),
                decorationBox = { innerTextField ->
                    LaunchedEffect(isFocused) {
                        if (!isFocused) {
                            // Проверяем, является ли курсор в конце текста
                            val isCursorAtEnd =
                                text.value.selection.collapsed && text.value.selection.start == text.value.text.length
                            if (!isCursorAtEnd) {
                                // Прокручиваем курсор в конец
                                coroutineScope.launch {
                                    // TODO(Надо сделать так чтобы прокрутка была по горизонтали)
                                    val offset = text.value.selection.start + 1 // измените эту строку, чтобы установить нужное значение offset
                                    val newSelection = TextRange(offset, offset)
                                    text.value = text.value.copy(selection = newSelection)
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
                            modifier = Modifier.padding(end = 5.dp)
                        )
                        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                            innerTextField()
                        }
                    }
                },
                cursorBrush = SolidColor(TextColor)
            )
            Box(
                modifier = Modifier
                    .height(1.dp)
                    .background(TextColor)
                    .fillMaxWidth()
            )
        }

        // Контейнер с добавление собственной статьи
        Box(
            modifier = Modifier
                .size(30.dp, 40.dp)
                .background(color = Color.Transparent, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(iconDocument),
                contentDescription = "Добавить статью",
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        if (iconDocument == R.drawable.pdf_added_white)
                            iconDocument = R.drawable.pdf_add_white
                        else
                            launcher.launch("application/pdf")
                    },
                tint = TextColor,
            )
        }

        // Контейнер с иконкой поиска статьи
        Box(
            modifier = Modifier
                .size(30.dp, 40.dp)
                .background(color = Color.Transparent, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.search_white),
                contentDescription = "Найти статьи",
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                    },
                tint = TextColor,
            )
        }
    }
}

