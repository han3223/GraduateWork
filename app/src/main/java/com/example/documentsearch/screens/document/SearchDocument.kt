package com.example.documentsearch.screens.document

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.documentsearch.R
import com.example.documentsearch.screens.document.addDocument.isClickBlock
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.ORDINARY_TEXT
import com.example.documentsearch.ui.theme.TextColor

class SearchDocument {
    @Composable
    fun SearchEngine() {
        val mainContainerModifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth()

        var text by remember { mutableStateOf(TextFieldValue("")) }

        Row(
            modifier = mainContainerModifier,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                SearchTextField(text) { text = it }
            }
            Box(modifier = Modifier.size(30.dp), contentAlignment = Alignment.BottomCenter) {
                AddedDocument()
            }
            Box(modifier = Modifier.size(30.dp), contentAlignment = Alignment.BottomCenter) {
                SearchButton()
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun SearchTextField(text: TextFieldValue, onTextChange: (TextFieldValue) -> Unit) {
        var isFocused by remember { mutableStateOf(false) }
        val textFieldModifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 1.dp)
            .background(color = MainColor)
            .onFocusChanged { isFocused = it.isFocused }

        val textContainerModifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 1.dp)

        val lineModifier = Modifier
            .height(1.dp)
            .background(TextColor)
            .fillMaxWidth()

        BasicTextField(
            value = text,
            onValueChange = { onTextChange(it) },
            enabled = isClickBlock.value,
            modifier = textFieldModifier,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = ORDINARY_TEXT,
            cursorBrush = SolidColor(TextColor),
            decorationBox = { innerTextField ->
                Column {
                    Row(
                        modifier = textContainerModifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = TextColor,
                            modifier = Modifier.padding(end = 5.dp)
                        )
                        Row(
                            modifier = Modifier
                                .height(30.dp)
                                .horizontalScroll(rememberScrollState()),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            innerTextField()
                        }
                    }
                    Box(modifier = lineModifier)
                }
            },
        )
    }

    @Composable
    private fun AddedDocument() {
        var iconDocument by remember { mutableIntStateOf(R.drawable.pdf_add_white) }
        val addedFiles = ActivityResultContracts.GetContent()
        val launcher = rememberLauncherForActivityResult(contract = addedFiles) { uri: Uri? ->
            uri?.let { _ ->
                iconDocument = R.drawable.pdf_added_white
            }
        }

        val iconModifier = Modifier
            .size(25.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    if (iconDocument == R.drawable.pdf_added_white)
                        iconDocument = R.drawable.pdf_add_white
                    else
                        launcher.launch("application/pdf")
                })
            }

        Icon(
            painter = painterResource(iconDocument),
            contentDescription = "Добавить статью",
            modifier = iconModifier,
            tint = TextColor,
        )
    }

    @Composable
    private fun SearchButton() {
        Icon(
            painter = painterResource(R.drawable.search_white),
            contentDescription = "Найти статьи",
            modifier = Modifier.size(25.dp),
            tint = TextColor,
        )
    }
}