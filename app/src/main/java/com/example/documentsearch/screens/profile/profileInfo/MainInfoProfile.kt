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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.navigator.Navigator
import com.example.documentsearch.R
import com.example.documentsearch.patterns.EditSize
import com.example.documentsearch.patterns.EditText
import com.example.documentsearch.patterns.profile.ProfileFactory
import com.example.documentsearch.prototypes.UserProfilePrototype
import com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens.ChangingEmailScreen
import com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens.ChangingNumberPhoneScreen
import com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens.ChangingPasswordScreen
import com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens.ChangingPersonalInfoScreen
import com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens.ChangingPersonalNameScreen
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.AdditionalMainColor
import com.example.documentsearch.ui.theme.HEADING_TEXT
import com.example.documentsearch.ui.theme.HIGHLIGHTING_BOLD_TEXT
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.ORDINARY_TEXT
import com.example.documentsearch.ui.theme.SECONDARY_TEXT
import com.example.documentsearch.ui.theme.TextColor
import kotlinx.coroutines.launch


// TODO(Нужно переписать и наладить работу)
data class MainInfoProfile(val profile: UserProfilePrototype, val navigator: Navigator) {
    private var widthContent: Dp = 0.dp
    private val editSize = EditSize()
    private val profileFactory = ProfileFactory()

    @Composable
    fun Info(lazyListState: LazyListState) {
        val density = LocalDensity.current
        var dragStart by remember { mutableFloatStateOf(0f) }
        var widthProfilePicture by remember { mutableStateOf(110.dp) }

        var widthContent by remember { mutableStateOf(0.dp) }
        this.widthContent = widthContent

        var isOpen by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()

        val pointerInput: Modifier = if (isOpen) {
            Modifier.pointerInput(Unit) {
                detectVerticalDragGestures(
                    onDragStart = { dragStart = it.y }
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

        // Основной контейнер с информацией о пользователе
        Box(
            modifier = pointerInput
                .padding(top = 5.dp)
                .zIndex(2f)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(33.dp, 33.dp, 18.dp, 18.dp))
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
                            FullName(animatedWidthProfilePicture)
                            PersonalName()
                        }
                    }
                    // Аватарка
                    Box(
                        modifier = Modifier
                            .zIndex(1f)
                            .align(Alignment.TopEnd)
                            .padding(
                                top = 10.dp,
                                bottom = 10.dp + editSize.getPaddingAfterAnimation(
                                    widthContent = widthContent,
                                    animatedWidth = animatedWidthProfilePicture,
                                    afterPadding = 60
                                )
                            )
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
                                    modifier = Modifier.size(animatedEditPhotoBox / 2)
                                )
                            }
                        }
                    }
                }
                Separator()

                ProfileInfo()
                Separator()

                PhoneNumber()
                Separator()

                Email()
                Separator()

                Password()
                Separator()
            }
        }
    }

    @Composable
    private fun FullName(animatedWidthProfilePicture: Dp) {
        Text(
            text = "${profile.lastName} ${profile.firstName} ${profile.patronymic}",
            style = HEADING_TEXT,
            modifier = Modifier
                .padding(
                    start = editSize.getPaddingAfterAnimation(
                        widthContent = widthContent,
                        animatedWidth = animatedWidthProfilePicture,
                        afterPadding = 10
                    )
                )
        )
    }

    @Composable
    private fun PersonalName() {
        Column(modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        navigator.push(ChangingPersonalNameScreen())
                    }
                )
            }
        ) {
            profileFactory.StandardBlock(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                label = "Пользовательское имя:",
                styleLabel = HIGHLIGHTING_BOLD_TEXT,
                value = "@${profile.personalName}",
                styleValue = ORDINARY_TEXT
            )
            Text(
                text = "Нажмите чтобы изменить",
                style = SECONDARY_TEXT,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 7.dp, bottom = 5.dp)
            )
        }
    }

    @Composable
    private fun ProfileInfo() {
        Column(modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(
                onTap = { navigator.push(ChangingPersonalInfoScreen()) }
            )
        }) {
            profileFactory.StandardBlock(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 10.dp, end = 20.dp),
                label = "Информация:",
                styleLabel = HIGHLIGHTING_BOLD_TEXT,
                value = profile.personalInfo ?: "",
                styleValue = ORDINARY_TEXT
            )
            Hint()
        }
    }

    @Composable
    private fun PhoneNumber() {
        Column(modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(
                onTap = { navigator.push(ChangingNumberPhoneScreen()) }
            )
        }) {
            profileFactory.StandardBlock(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 10.dp, end = 20.dp),
                label = "Номер телефона:",
                styleLabel = HIGHLIGHTING_BOLD_TEXT,
                value = EditText().getMaskNumberPhone(profile.phoneNumber),
                styleValue = ORDINARY_TEXT
            )
            Hint()
        }
    }

    @Composable
    private fun Email() {
        Column(modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(
                onTap = { navigator.push(ChangingEmailScreen()) },
            )
        }) {
            profileFactory.StandardBlock(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 10.dp, end = 20.dp),
                label = "Email:",
                styleLabel = HIGHLIGHTING_BOLD_TEXT,
                value = profile.email,
                styleValue = ORDINARY_TEXT
            )
            Hint()
        }
    }

    @Composable
    private fun Password() {
        Column(modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(
                onTap = {
                    navigator.push(ChangingPasswordScreen())
                },
            )
        }) {
            profileFactory.StandardBlock(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 10.dp, end = 20.dp),
                label = "Пароль:",
                styleLabel = HIGHLIGHTING_BOLD_TEXT,
                value = "*********",
                styleValue = ORDINARY_TEXT
            )
            Hint()
        }
    }

    @Composable
    private fun Hint() {
        Text(
            text = "Нажмите, чтобы создать новый пароль",
            style = SECONDARY_TEXT,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 5.dp, bottom = 5.dp)
        )
    }

    @Composable
    private fun Separator() {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(AdditionalColor)
        )
    }
}