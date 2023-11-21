package com.example.documentsearch.screens.messenger

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.documentsearch.R
import com.example.documentsearch.dataClasses.Messenger
import com.example.documentsearch.dataClasses.Profile
import com.example.documentsearch.navbar.NavigationItem
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.TextColor

/**
 * Функция отображает блок с мессенджером
 */
@SuppressLint("RememberReturnType")
@Composable
fun MessengerScreen(
    navController: NavController,
    listMessenger: List<Messenger>?,
    onMessengerChange: (Messenger) -> Unit,
    profile: Profile?
) {
    if (profile != null) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MainColorLight)
        ) {
            listMessenger?.forEach { messenger ->
                if (messenger.listMessage.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .padding(20.dp, 10.dp, 20.dp, 10.dp)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onTap = {
                                        navController.navigate(NavigationItem.Communication.route)
                                        onMessengerChange(messenger)
                                    },
                                )
                            },
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(65.dp)
                                .background(AdditionalColor, CircleShape)
                        )
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = "${messenger.interlocutor.lastName} ${messenger.interlocutor.firstName}",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                                    fontWeight = FontWeight(600),
                                    color = TextColor,
                                ),
                            )
                            Text(
                                text = "${messenger.listMessage.last().message.substring(0..30)}...",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                                    fontWeight = FontWeight(600),
                                    color = TextColor,
                                ),
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(AdditionalColor)
                    )
                }
            }
        }
    }
    else
        Text(text = "Надо для начала зарегистрироваться")
}