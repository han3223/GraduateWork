package com.example.documentsearch.screens.profile.profileInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.documentsearch.R
import com.example.documentsearch.listDocumet
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.AdditionalMainColor
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.TextColor

/**
 * Функция показывает список выпущенных документаций у конкретного пользователя
 */
@Composable
fun PersonalDocumentation() {
    var documentationsVisible by remember { mutableIntStateOf(1) } // Число видимых документаций

    Spacer(modifier = Modifier.height(10.dp))
    Box(
        modifier = Modifier
            .zIndex(2f)
            .fillMaxWidth()
            .heightIn(0.dp, 305.dp)
            .clip(shape = RoundedCornerShape(size = 33.dp))
            .background(color = MainColorLight)
    ) {
        LazyColumn {
            item(0) {
                Text(
                    text = "Выпущенная документация",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                        fontWeight = FontWeight(600),
                        color = TextColor,
                    ),
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
                )
                // Линия разделения
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(AdditionalColor)
                )
            }
            items(listDocumet.size) { index ->
                if (index <= documentationsVisible) {
                    Row(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(84.dp)
                                .background(
                                    color = AdditionalColor,
                                    shape = RoundedCornerShape(size = 22.dp)
                                )
                        ) {
                            // TODO(Сюда надо будет положить картинку документа из базы данных)
                        }
                        Text(
                            text = listDocumet[index].document.title,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                                fontWeight = FontWeight(600),
                                color = TextColor,
                                textAlign = TextAlign.Start
                            ),
                            modifier = Modifier
                                .align(Alignment.Top)
                                .weight(1f)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.download),
                            contentDescription = "Скачать",
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.Bottom)
                                .clickable {
                                    //TODO(Надо сделать скачивание из базы данных)
                                },
                            tint = TextColor
                        )
                    }

                    if (index <= documentationsVisible) {
                        // Линия разделения
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(AdditionalColor)
                        )
                    }
                    if (index == documentationsVisible) {
                        Text(
                            text = "Показать ещё",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                                fontWeight = FontWeight(600),
                                color = TextColor,
                                textAlign = TextAlign.Center,
                            ),
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxWidth()
                                .clickable { documentationsVisible += 5 }
                                .background(
                                    AdditionalMainColor, RoundedCornerShape(33.dp)
                                )
                        )
                    }
                }
            }
        }
    }
}