package com.example.documentsearch.navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.documentsearch.R
import com.example.documentsearch.preferences.PreferencesManager
import com.example.documentsearch.preferences.emailKeyPreferences
import com.example.documentsearch.preferences.passwordKeyPreferences
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.addFriendNavbar
import com.example.documentsearch.ui.theme.documentsNavbar
import com.example.documentsearch.ui.theme.messengerNavbar
import com.example.documentsearch.ui.theme.profileNavbar

var activeItem = mutableStateOf(NavigationItem.Documents.selectionNavbar)

/**
 * Функция отображает navbar для приложения
 */
@Composable
fun Navbar(navController: NavController) {

    val context = LocalContext.current
    val preferencesManager = PreferencesManager(context)
    var height by remember { mutableStateOf(0.dp) }
    var width by remember { mutableStateOf(0.dp) }

    val navigationItems = listOf(
        NavigationItem.Documents,
        NavigationItem.Messenger,
        NavigationItem.AddUser,
        NavigationItem.Profile
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .width(330.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            navigationItems.forEach { item ->
                val isActive = item.selectionNavbar == activeItem.value
                val weight = when (item.route) {
                    "documents" -> 0.2f
                    "messenger" -> 0.3f
                    "add user" -> 0.3f
                    "profile" -> 0.2f
                    else -> 0f
                }

                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .weight(weight)
                        .onGloballyPositioned { coordinates ->
                            height = coordinates.size.height.dp
                            width = coordinates.size.width.dp
                        }
                ) {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .zIndex(2f)) {
                        GetShapeFromSVG(
                            svgCode = when (item.route) {
                                "documents" -> documentsNavbar
                                "messenger" -> messengerNavbar
                                "add user" -> addFriendNavbar
                                "profile" -> profileNavbar
                                else -> ""
                            },
                            colorShape = Color(0x4D000000),
                            stroke = Stroke(1f)
                        )
                    }
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .zIndex(1f)) {
                        GetShapeFromSVG(
                            svgCode = when (item.route) {
                                "documents" -> documentsNavbar
                                "messenger" -> messengerNavbar
                                "add user" -> addFriendNavbar
                                "profile" -> profileNavbar
                                else -> ""
                            },
                            colorShape = if (isActive) MainColorDark else MainColor,
                        )
                    }


                    Box(
                        modifier = Modifier
                            .zIndex(3f)
                            .align(Alignment.Center)
                            .padding(
                                start = if (item.route == "add user") width / 100 * 15 else 0.dp,
                                top = 0.dp,
                                end = if (item.route == "messenger") width / 100 * 15 else 0.dp,
                                bottom = 0.dp,
                            )
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onTap = {
                                        if (
                                            item.route == "profile" &&
                                            preferencesManager.getData(emailKeyPreferences) == null &&
                                            preferencesManager.getData(passwordKeyPreferences) == null
                                        )
                                            navController.navigate(NavigationItem.Login.route)
                                        else
                                            navController.navigate(item.route)
                                    },
                                )
                            },
                    ) {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = item.title,
                            modifier = Modifier.size(24.dp),
                            tint = Color.White,
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .size(80.dp)
                .padding(10.dp)
                .background(MainColorDark, shape = CircleShape)
                .align(Alignment.Center)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
//                         TODO(Добавить нажатие чтобы вызывалась форма добавления документа)
                        },
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.plus_white),
                contentDescription = "Добавить документ",
                modifier = Modifier.size(24.dp),
                tint = Color.White,
            )
        }
    }
}