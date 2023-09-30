package com.example.documentsearch.navbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.documentsearch.R
import com.example.documentsearch.ui.theme.MainColorDark

var activeItem = mutableStateOf(NavigationItem.Documents.route)

/**
 * Функция отображает navbar для приложения
 */
@Composable
fun Navbar(navController: NavController) {

    var height by remember { mutableStateOf(0.dp) }
    var width by remember { mutableStateOf(0.dp) }

    val navigationItems = listOf(
        NavigationItem.Documents,
        NavigationItem.Messenger,
        NavigationItem.AddUser,
        NavigationItem.Profile
    )

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            navigationItems.forEach { item ->
                val isActive = item.route == activeItem.value
                val weight = when (item.route) {
                    "documents" -> 0.2f
                    "messenger" -> 0.3f
                    "add user" -> 0.3f
                    "profile" -> 0.2f
                    else -> 0f
                }
                Box(
                    modifier = Modifier
                        .weight(weight)
                        .onGloballyPositioned { coordinates ->
                            height = coordinates.size.height.dp
                            width = coordinates.size.width.dp
                        }
                ) {
                    val painter = if (isActive) item.backgroundActive else item.background

                    Image(
                        painter = painterResource(id = painter),
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(
                                start = if (item.route == "add user") width / 100 * 15 else 0.dp,
                                top = height / 100 * 15,
                                end = if (item.route == "messenger") width / 100 * 15 else 0.dp,
                                bottom = 0.dp,
                            ),
                    ) {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = item.title,
                            modifier = Modifier
                                .size(24.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onTap = {
                                            navController.navigate(item.route)
                                        },
                                    )
                                },
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