package com.example.documentsearch.screens.document

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.rememberImagePainter
import com.example.documentsearch.R
import com.example.documentsearch.dataClasses.DocumentWithPercentage
import com.example.documentsearch.listDocumet
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.TextColor
import com.google.android.material.animation.ArgbEvaluatorCompat


// TODO(
//      Проблема с прокручиванием решена, но пришла мысль о том,
//      что если блок булет очень большим,
//      надо сделать так чтобы если блок больше экрана то прокручивать только к началу описания
//  )

/**
 * Функция хранит в себе список документов выгруженных из базы данных
 */
@ExperimentalAnimationApi
@Composable
fun DocumentScreen() {
    val rememberLazyScrollState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        state = rememberLazyScrollState
    ) {
        items(listDocumet.size) { index ->
            if (index == 0)
                Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
            ) {
                Document(listDocumet[index], rememberLazyScrollState) // Документ
            }

            if (index == listDocumet.lastIndex)
                Spacer(modifier = Modifier.height(90.dp))
        }
    }
}

/**
 * Отрисовывает документ
 * @param document Документ
 */
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Document(document: DocumentWithPercentage, rememberLazyScrollState: LazyListState) {
    var description by remember { mutableStateOf(false) }
    val animatedRotate by animateFloatAsState(if (description) 180f else 0f, label = "")
    val percent = rememberImagePainter(data = document.percentImage)
    val position = remember { mutableStateOf(0.dp) }
    /**
     * Блок с основной информацией
     */
    Box(
        modifier = Modifier
            .zIndex(2f)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 33.dp))
            .background(color = MainColorLight)
            .onGloballyPositioned { coordinates ->
                position.value = coordinates.positionInWindow().y.dp
            }
    ) {
        Column {
            /**
             * Отрисовывает картинку, название и процент соответствия
             */
            Row(
                modifier = Modifier
                    .padding(start = 11.dp, top = 11.dp, end = 11.dp)
                    .fillMaxWidth(),
            ) {
                /**
                 * Картинка документа
                 */
                Column(
                    modifier = Modifier
                        .padding(end = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .width(84.dp)
                            .height(84.dp)
                            .background(
                                color = AdditionalColor,
                                shape = RoundedCornerShape(size = 22.dp)
                            )
                    ) {
                        // TODO(Сюда надо будет положить картинку документа из базы данных)
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    /**
                     * Название документа
                     */
                    Box {
                        Text(
                            text = document.document.title,
                            style = TextStyle(
                                fontSize = 19.sp,
                                fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                                fontWeight = FontWeight(600),
                                color = TextColor,
                            ),
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                }

                /**
                 * Процент соответствия
                 */
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(30.dp)
                        .clip(CircleShape)
                        .zIndex(1f)
                ) {
                    Image(
                        painter = percent,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                    )
                    Text(
                        text = "${document.document.percent.toInt()}",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .zIndex(2f),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                            fontWeight = FontWeight(600),
                            color = Color.Black,
                        ),
                    )
                }
            }

            /**
             * Теги
             */
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(11.dp, 10.dp, 16.dp, 5.dp)
                    .clip(RoundedCornerShape(14.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .clip(RoundedCornerShape(14.dp))
                ) {
                    document.document.tags.forEach { tagText ->
                        Box(
                            modifier = Modifier
                                .background(
                                    color = MainColorDark,
                                    shape = RoundedCornerShape(size = 14.dp)
                                )
                        ) {
                            Text(
                                text = tagText,
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                                    fontWeight = FontWeight(600),
                                    color = TextColor,
                                ),
                                modifier = Modifier.padding(
                                    vertical = 5.dp,
                                    horizontal = 10.dp
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                    }
                }
            }

            /**
             * Отрисовывает дату, категорию и стрелочку с описанием
             */
            Row(
                modifier = Modifier
                    .padding(21.dp, 0.dp, 11.dp, 12.dp)
                    .fillMaxWidth()
                    .background(Color.Transparent, RoundedCornerShape(14.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                /**
                 * Дата и категория
                 */
                Row {
                    Box {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .background(color = MainColorDark, shape = CircleShape)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Box {
                                Text(
                                    text = document.document.category,
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                                        fontWeight = FontWeight(600),
                                        color = Color.White,
                                    ),
                                )
                            }
                        }
                    }
                    Text(
                        text = "  |  ",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                            fontWeight = FontWeight(600),
                            color = TextColor,
                        ),
                    )
                    Text(
                        text = document.document.date,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                            fontWeight = FontWeight(600),
                            color = TextColor,
                        ),
                    )
                }

                /**
                 * Стрелочка с описанием
                 */
                Row {
                    Box(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = "Open",
                            modifier = Modifier
                                .size(90.dp)
                                .padding(start = 5.dp)
                                .clickable {
                                    description = !description
                                }
                                .align(Alignment.Center)
                                .rotate(animatedRotate),
                            tint = MainColorDark,
                        )
                    }
                }

            }
        }
    }

    /**
     * Блок с анимацией описания
     */
    AnimatedVisibility(
        visible = description,
        enter = slideInVertically() + expandVertically(expandFrom = Alignment.Top) + fadeIn(),
        exit = slideOutVertically() + shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(),
        modifier = Modifier
            .zIndex(1f)
            .padding(top = 90.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(size = 33.dp))
                .background(color = MainColorDark),
        ) {
            Column(modifier = Modifier.padding(21.dp, 100.dp, 15.dp, 0.dp)) {
                Text(
                    text = "Описание",
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Text(
                    text = document.document.description,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                        fontWeight = FontWeight(600),
                        color = TextColor,
                    ),
                    modifier = Modifier.padding(bottom = 3.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.download),
                        contentDescription = "Скачать",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                //TODO(Надо сделать скачивание из базы данных)
                            },
                        tint = TextColor
                    )
                }
            }
        }
    }

    val heightWindow = LocalConfiguration.current.screenHeightDp.dp.value
    LaunchedEffect(key1 = description) {
        if (description) {
            if (position.value.value > heightWindow * 1.75) {
                val scroll = position.value.value - heightWindow * 1.55
                rememberLazyScrollState.animateScrollBy(scroll.toFloat())
            }
            // Анимация появления завершена
        } else {
            // Анимация исчезновения завершена
        }
    }
}

/**
 * Функция для отображения эволюции цвета от начального до конечного(не используется)
 */
fun evaluateColor(fraction: Float, startColor: Color, endColor: Color): Color {
    return Color(
        ArgbEvaluatorCompat().evaluate(fraction, startColor.toArgb(), endColor.toArgb()).toLong()
    )
}