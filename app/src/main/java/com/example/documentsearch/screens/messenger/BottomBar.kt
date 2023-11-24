package com.example.documentsearch.screens.messenger

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.documentsearch.R
import com.example.documentsearch.navbar.GetShapeFromSVG
import com.example.documentsearch.patterns.authentication.StandardTextField
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.TextColor
import com.example.documentsearch.ui.theme.headerLeft
import com.example.documentsearch.ui.theme.headerRight
import kotlinx.coroutines.launch

@Composable
fun BottomBar(onMessageChange: (String) -> Unit, rememberScrollState: ScrollState) {
    var message by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .rotate(180f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Левый угол
            Box {
                Box(
                    modifier = Modifier
                        .size(33.dp)
                        .zIndex(2f)
                ) {
                    GetShapeFromSVG(svgCode = headerLeft, colorShape = MainColor)
                }
                // Тень
                Box(
                    modifier = Modifier
                        .size(33.dp)
                        .zIndex(1f)
                ) {
                    GetShapeFromSVG(
                        svgCode = "M44.5 1C14 1 0 19 0 39",
                        colorShape = Color(0xD9000000),
                        stroke = Stroke(width = 2f)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .height(1.5.dp)
                    .weight(1f)
                    .background(Color(0xD9000000))
            )
            // Правый угол
            Box {
                Box(
                    modifier = Modifier
                        .size(33.dp)
                        .zIndex(2f)
                ) {
                    GetShapeFromSVG(svgCode = headerRight, colorShape = MainColor)
                }
                // Тень
                Box(
                    modifier = Modifier
                        .size(33.dp)
                        .zIndex(1f)
                ) {
                    GetShapeFromSVG(
                        svgCode = "M0 1C30.5 1 44.5 19 44.5 39",
                        colorShape = Color(0xD9000000),
                        stroke = Stroke(width = 2f)
                    )
                }
            }

        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(MainColor)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp, 20.dp, 10.dp)
                    .align(Alignment.TopCenter),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StandardTextField(
                    value = message,
                    onValueChanged = { message = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .heightIn(40.dp, 160.dp)
                        .background(AdditionalColor, RoundedCornerShape(20.dp)),
                    keyboardOptions = KeyboardOptions.Default,
                    keyboardActions = KeyboardActions.Default,
                    onFocusChange = {},
                    validColor = TextColor,
                    activeTextField = false,
                    textStyle = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                        fontWeight = FontWeight(600),
                        color = Color.Black,
                    ),
                    singleLine = false,
                    alwaysDisable = true,
                    modifierText = Modifier.padding(top = 10.dp, start = 3.dp)
                )
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(MainColorDark, CircleShape)
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                if (message.isNotEmpty()) {
                                    coroutineScope.launch {
                                        rememberScrollState.scrollTo(rememberScrollState.maxValue)
                                    }
                                    onMessageChange(message)
                                    message = ""
                                }
                            })
                        }
                )
            }
        }
    }
}