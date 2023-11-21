package com.example.documentsearch.screens.addUser

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.documentsearch.R
import com.example.documentsearch.api.apiRequests.MessengersRequests
import com.example.documentsearch.api.apiRequests.ProfilesRequests
import com.example.documentsearch.dataClasses.AddMessenger
import com.example.documentsearch.dataClasses.AnotherUser
import com.example.documentsearch.dataClasses.Messenger
import com.example.documentsearch.dataClasses.Profile
import com.example.documentsearch.patterns.profile.StandardBlock
import com.example.documentsearch.screens.profile.profileInfo.getMaskNumberPhone
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.AdditionalMainColor
import com.example.documentsearch.ui.theme.AdditionalMainColorDark
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.TextColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun ProfileInfo(
    navController: NavHostController,
    anotherProfile: AnotherUser,
    profile: Profile,
    onMessengerChange: (Messenger) -> Unit
) {
    val density = LocalDensity.current // Нужен для определения длины контейнера
    var widthContent by remember { mutableStateOf(0.dp) } // Длина контента в контейнере


    Spacer(modifier = Modifier.height(10.dp))
    // Основной контейнер с информацией о пользователе
    Box(
        modifier = Modifier
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
                            text = "${anotherProfile.lastName} ${anotherProfile.firstName} ${anotherProfile.patronymic}",
                            style = TextStyle(
                                fontSize = 21.sp,
                                fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                                fontWeight = FontWeight(600),
                                color = TextColor,
                            ),
                            modifier = Modifier.padding(start = 10.dp)
                        )

                        Column() {
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
                                value = "@${anotherProfile.personalName}",
                                styleValue = TextStyle(
                                    fontSize = 15.sp,
                                    fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                                    fontWeight = FontWeight(600),
                                    color = TextColor,
                                )
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
                            bottom = 45.dp
                        )
                        .align(Alignment.TopEnd)
                        .size(
                            widthContent,
                            widthContent / 1.5f
                        )
                        .background(
                            color = MainColorDark,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 10.dp, bottom = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .background(AdditionalMainColor, CircleShape)
                                .pointerInput(Unit) {
                                    detectTapGestures(onTap = {
                                        CoroutineScope(Dispatchers.Main).launch {
                                            val request = MessengersRequests().addMessenger(
                                                AddMessenger(user = profile.id!!, interlocutor = anotherProfile.id)
                                            )
                                            if (request != null) {
                                                val messenger = Messenger(request.id, anotherProfile, mutableListOf())
                                                onMessengerChange(messenger)
                                            }
                                        }
                                    })
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.message_white),
                                contentDescription = null,
                                tint = TextColor,
                                modifier = Modifier.size(17.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .background(AdditionalMainColor, CircleShape)
                                .pointerInput(Unit) {
                                    detectTapGestures(onTap = {
                                        CoroutineScope(Dispatchers.Main).launch {
                                            val request = ProfilesRequests().addFriend(profile.email, anotherProfile.id.toString())
                                        }
                                    })
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.add_user_white),
                                contentDescription = null,
                                tint = TextColor,
                                modifier = Modifier.size(17.dp)
                            )
                        }
                    }
                }
            }

            // Информация о пользователе
            Column {
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
                    value = anotherProfile.personalInfo ?: "",
                    styleValue = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                        fontWeight = FontWeight(600),
                        color = TextColor,
                    )
                )
            }

            // Линия разделения
            Spacer(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(AdditionalColor)
            )
            // Номер телефона
            Column {
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
                    value = getMaskNumberPhone(anotherProfile.numberPhone),
                    styleValue = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                        fontWeight = FontWeight(600),
                        color = TextColor,
                    )
                )
            }

            // Линия разделения
            Spacer(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(AdditionalColor)
            )
            // Email
            Column {
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
                    value = anotherProfile.email,
                    styleValue = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                        fontWeight = FontWeight(600),
                        color = TextColor,
                    )
                )
            }
            // Линия разделения
            Spacer(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(AdditionalColor)
            )

            // Контейнер для тегов пользователя
            if (!anotherProfile.tags.isNullOrEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 10.dp, 20.dp, 10.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .height(35.dp)
                        .background(AdditionalMainColorDark),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(35.dp)
                            .clip(RoundedCornerShape(17.dp))
                            .shadow(
                                40.dp,
                                RoundedCornerShape(14.dp),
                                ambientColor = Color.White,
                                spotColor = Color.White
                            )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .horizontalScroll(rememberScrollState())
                            .clip(RoundedCornerShape(14.dp))
                            .padding(horizontal = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        anotherProfile.tags.forEach {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = MainColorDark,
                                        shape = RoundedCornerShape(size = 14.dp)
                                    )
                                    .border(1.dp, Color(0xCC354643), RoundedCornerShape(14.dp))
                            ) {
                                Text(
                                    text = it.toString(),
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
            }
        }
    }
}