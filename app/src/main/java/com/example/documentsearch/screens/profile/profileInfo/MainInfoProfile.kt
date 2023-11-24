package com.example.documentsearch.screens.profile.profileInfo

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.documentsearch.R
import com.example.documentsearch.prototypes.ProfilePrototype
import com.example.documentsearch.navbar.NavigationItem
import com.example.documentsearch.patterns.profile.StandardBlock
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.AdditionalMainColor
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.TextColor
import kotlinx.coroutines.launch

/**
 * Функция отображает основную исформацию о пользователе
 * @param navController Контроллер для навигации
 * @param profile Профиль пользователя
 * @param lazyListState Скролл на экране
 */
@Composable
fun MainInfoProfile(
    navController: NavHostController,
    profile: ProfilePrototype,
    lazyListState: LazyListState
) {
    val density = LocalDensity.current // Нужен для определения длины контейнера
    var dragStart by remember { mutableFloatStateOf(0f) } // Начальная точка свайпа вниз
    var widthProfilePicture by remember { mutableStateOf(110.dp) } // Длина аватарки
    var widthContent by remember { mutableStateOf(0.dp) } // Длина контента в контейнере
    var isOpen by remember { mutableStateOf(false) } // Длина контента в контейнере
    val coroutineScope = rememberCoroutineScope()

    val pointerInput: Modifier = if (isOpen) {
        Modifier.pointerInput(Unit) {
            detectVerticalDragGestures(
                onDragStart = {
                    dragStart = it.y

                },
            ) { change, dragAmount ->
                // Обработка свайпа и изменение длины аватарки
                if (isOpen && dragAmount < 0 && widthProfilePicture > 110.dp) {
                    if (widthContent - widthProfilePicture > 50.dp) {
                        isOpen = !isOpen
                        widthProfilePicture = 110.dp
                    } else
                        widthProfilePicture -= ((dragStart - 0 + change.pressure) / 100).dp
                }
            }
        }
    } else {
        Modifier
    }

    // Длина аватарки с анимацией
    val animatedWidthProfilePicture: Dp by animateDpAsState(
        targetValue = if (widthProfilePicture <= 110.dp) widthProfilePicture else widthContent,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy),
        label = ""
    )

    val animatedEditPhotoBox by animateDpAsState(
        targetValue = if (animatedWidthProfilePicture - 110.dp <= 60.dp) (animatedWidthProfilePicture - 110.dp) else 60.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy),
        label = ""
    )

    Spacer(modifier = Modifier.height(10.dp))
    // Основной контейнер с информацией о пользователе
    Box(
        modifier = pointerInput
            .zIndex(2f)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 33.dp))
            .background(color = MainColorLight)

    ) {
        // Список информации о пользователе
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(top = 10.dp, bottom = 30.dp)
        ) {
            // Контейнер с ФИО, Пользовательским именем и аватаркой
            Box(
                modifier = Modifier
                    .padding(20.dp, 0.dp, 20.dp, 0.dp)
                    .onSizeChanged {
                        // Определение размера контента
                        widthContent = with(density) { it.width.toDp() }
                    }
            ) {
                Row(
                    modifier = Modifier
                        .zIndex(2f)
                        .fillMaxWidth()
                        .align(Alignment.BottomStart),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Контейнер с ФИО, Пользовательским именем
                    Column(
                        modifier = Modifier.fillMaxWidth(0.6f)
                    ) {
                        Text(
                            text = "${profile.lastName} ${profile.firstName} ${profile.patronymic}",
                            style = TextStyle(
                                fontSize = 21.sp,
                                fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                                fontWeight = FontWeight(600),
                                color = TextColor,
                            ),
                            modifier = Modifier
                                .padding(
                                    start = getPaddingAfterAnimation(
                                        widthContent = widthContent,
                                        animatedWidth = animatedWidthProfilePicture,
                                        afterPadding = 10
                                    )
                                )
                        )
                        Column(modifier = Modifier.pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    navController.navigate(NavigationItem.ChangePersonalName.route)
                                },
                            )
                        }) {
                            StandardBlock(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp),
                                label = "Пользовательское имя:",
                                styleLabel = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                                    fontWeight = FontWeight(600),
                                    color = TextColor,
                                ),
                                value = "@${profile.personalName}",
                                styleValue = TextStyle(
                                    fontSize = 15.sp,
                                    fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                                    fontWeight = FontWeight(600),
                                    color = TextColor,
                                )
                            )
                            Text(
                                text = "Нажмите чтобы изменить",
                                style = TextStyle(
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                                    fontWeight = FontWeight(500),
                                    color = AdditionalColor,
                                    textAlign = TextAlign.Start
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 7.dp, bottom = 5.dp)
                            )
                        }
                    }
                }
                // Аватарка
                Box(
                    modifier = Modifier
                        .zIndex(1f)
                        .padding(
                            top = 10.dp,
                            bottom = 10.dp + getPaddingAfterAnimation(
                                widthContent = widthContent,
                                animatedWidth = animatedWidthProfilePicture,
                                afterPadding = 60
                            )
                        )
                        .align(Alignment.TopEnd)
                        .size(
                            110.dp + (animatedWidthProfilePicture - 110.dp),
                            110.dp + (animatedWidthProfilePicture - 110.dp) / 2
                        )
                        .pointerInput(Unit) {
                            detectTapGestures {
                                if (!isOpen) {
                                    isOpen = true
                                    widthProfilePicture =
                                        if (widthProfilePicture > 110.dp) 100.dp else widthContent
                                }

                                coroutineScope.launch { lazyListState.animateScrollToItem(0) }
                            }
                        }
                        .background(
                            color = MainColorDark,
                            shape = RoundedCornerShape(55.dp - if ((animatedWidthProfilePicture - 110.dp) <= 50.dp) (animatedWidthProfilePicture - 110.dp) else 45.dp)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(animatedEditPhotoBox)
                            .padding(end = 10.dp, bottom = 10.dp)
                            .background(AdditionalMainColor, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        if (animatedWidthProfilePicture - 110.dp >= 30.dp) {
                            Icon(
                                painter = painterResource(id = R.drawable.edit),
                                contentDescription = null,
                                tint = TextColor,
                                modifier = Modifier.size(animatedEditPhotoBox/2)
                            )
                        }
                    }
                }
            }

            // Линия разделения
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(AdditionalColor)
            )
            // Информация о пользователе
            Column(modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        navController.navigate(NavigationItem.ChangePersonalInfo.route)
                    },
                )
            }) {
                StandardBlock(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 10.dp, end = 20.dp),
                    label = "Информация:",
                    styleLabel = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                        fontWeight = FontWeight(600),
                        color = TextColor,
                    ),
                    value = profile.personalInfo?: "",
                    styleValue = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                        fontWeight = FontWeight(600),
                        color = TextColor,
                    )
                )
                Text(
                    text = "Нажмите, чтобы добавить информацию о себе",
                    style = TextStyle(
                        fontSize = 11.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                        fontWeight = FontWeight(500),
                        color = AdditionalColor,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 7.dp, bottom = 5.dp)
                )
            }

            // Линия разделения
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(AdditionalColor)
            )
            // Номер телефона
            Column(modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        navController.navigate(NavigationItem.ChangeNumberPhone.route)
                    },
                )
            }) {
                StandardBlock(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 10.dp, end = 20.dp),
                    label = "Номер телефона:",
                    styleLabel = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                        fontWeight = FontWeight(600),
                        color = TextColor,
                    ),
                    value = getMaskNumberPhone(profile.telephoneNumber),
                    styleValue = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                        fontWeight = FontWeight(600),
                        color = TextColor,
                    )
                )
                Text(
                    text = "Нажмите, чтобы сменить номер телефона",
                    style = TextStyle(
                        fontSize = 11.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                        fontWeight = FontWeight(500),
                        color = AdditionalColor,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 7.dp, bottom = 5.dp)
                )
            }

            // Линия разделения
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(AdditionalColor)
            )
            // Email
            Column(modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        navController.navigate(NavigationItem.ChangeEmail.route)
                    },
                )
            }) {
                StandardBlock(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 10.dp, end = 20.dp),
                    label = "Email:",
                    styleLabel = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                        fontWeight = FontWeight(600),
                        color = TextColor,
                    ),
                    value = profile.email,
                    styleValue = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                        fontWeight = FontWeight(600),
                        color = TextColor,
                    )
                )
                Text(
                    text = "Нажмите, чтобы сменить email",
                    style = TextStyle(
                        fontSize = 11.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                        fontWeight = FontWeight(500),
                        color = AdditionalColor,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 7.dp, bottom = 5.dp)
                )
            }

            // Линия разделения
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(AdditionalColor)
            )
            // Пароль
            Column(modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        navController.navigate(NavigationItem.ChangePassword.route)
                    },
                )
            }) {
                StandardBlock(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 10.dp, end = 20.dp),
                    label = "Пароль:",
                    styleLabel = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                        fontWeight = FontWeight(600),
                        color = TextColor,
                    ),
                    value = "*********",
                    styleValue = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                        fontWeight = FontWeight(600),
                        color = TextColor,
                    )
                )
                Text(
                    text = "Нажмите, чтобы создать новый пароль",
                    style = TextStyle(
                        fontSize = 11.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                        fontWeight = FontWeight(500),
                        color = AdditionalColor,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 7.dp, bottom = 5.dp)
                )
            }

            // Линия разделения
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(AdditionalColor)
            )
        }
    }
}

fun getMaskNumberPhone(numberPhone: String): String {
    var maskedNumber = ""
    numberPhone.forEachIndexed { index, char ->
        when (index) {
            0 -> maskedNumber = "+$char"
            1 -> maskedNumber += " ($char"
            4 -> maskedNumber += ") $char"
            7, 9 -> maskedNumber += "-$char"
            else -> maskedNumber += char
        }
    }

    return maskedNumber
}

/**
 * Функция позволяет расчитать конечный отступ и после анимации
 * @param widthContent Длина контейнера
 * @param animatedWidth Длина увеличиваемого блока
 * @param afterPadding Конечный отступ
 */
@Composable
fun getPaddingAfterAnimation(widthContent: Dp, animatedWidth: Dp, afterPadding: Int): Dp {
    return kotlin.math.abs(((1 - ((widthContent - animatedWidth) / (widthContent - 110.dp))).dp * afterPadding).value.toInt()).dp
}