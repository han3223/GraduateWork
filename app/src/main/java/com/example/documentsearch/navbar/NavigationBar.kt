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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.documentsearch.R
import com.example.documentsearch.ui.theme.ADD_FRIEND_NAVBAR
import com.example.documentsearch.ui.theme.DOCUMENTS_NAVBAR
import com.example.documentsearch.ui.theme.MESSENGER_NAVBAR
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.PROFILE_NAVBAR

class NavigationBar {
    private var activeItem = mutableStateOf(NavigationItem.Documents.selectionNavbar)

    private val navigationItems = listOf(
        NavigationItem.Documents,
        NavigationItem.Messenger,
        NavigationItem.AddUser,
        NavigationItem.Profile
    )

    @Composable
    fun Content(onSelectedScreen: (String) -> Unit) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            MainNavigation { onSelectedScreen(it) }
            AddDocument()
        }
    }

    @Composable
    private fun MainNavigation(onSelectedScreen: (String) -> Unit) {
        var height by remember { mutableStateOf(0.dp) }
        var width by remember { mutableStateOf(0.dp) }

        Row(
            modifier = Modifier.width(330.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            navigationItems.forEach { item ->
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
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                onSelectedScreen(item.route)
                                activeItem.value = item.route
                            })
                        },
                    contentAlignment = Alignment.Center
                ) {
                    BorderNavigationItems(item.route)
                    BodyNavigationItem(item.route)
                    IconNavigationItem(item, width)
                }
            }
        }
    }

    @Composable
    private fun BorderNavigationItems(route: String) {
        Box(modifier = Modifier
            .fillMaxSize()
            .zIndex(2f)) {
            val svgFactory = SVGFactory()
            svgFactory.GetShapeFromSVG(
                svgCode = when (route) {
                    "documents" -> DOCUMENTS_NAVBAR
                    "messenger" -> MESSENGER_NAVBAR
                    "add user" -> ADD_FRIEND_NAVBAR
                    "profile" -> PROFILE_NAVBAR
                    else -> ""
                },
                colorShape = Color(0x4D000000),
                stroke = Stroke(1f)
            )
        }
    }

    @Composable
    private fun BodyNavigationItem(route: String) {
        val isActive = route == activeItem.value
        Box(modifier = Modifier
            .fillMaxSize()
            .zIndex(1f)) {
            val svgFactory = SVGFactory()
            svgFactory.GetShapeFromSVG(
                svgCode = when (route) {
                    "documents" -> DOCUMENTS_NAVBAR
                    "messenger" -> MESSENGER_NAVBAR
                    "add user" -> ADD_FRIEND_NAVBAR
                    "profile" -> PROFILE_NAVBAR
                    else -> ""
                },
                colorShape = if (isActive) MainColorDark else MainColor,
            )
        }
    }

    @Composable
    private fun IconNavigationItem(navigationItem: NavigationItem, width: Dp) {
        Box(
            modifier = Modifier
                .zIndex(3f)
                .padding(
                    start = if (navigationItem.route == "add user") width / 100 * 15 else 0.dp,
                    top = 0.dp,
                    end = if (navigationItem.route == "messenger") width / 100 * 15 else 0.dp,
                    bottom = 0.dp,
                )
        ) {
            Icon(
                painter = painterResource(navigationItem.icon),
                contentDescription = navigationItem.title,
                modifier = Modifier.size(24.dp),
                tint = Color.White,
            )
        }
    }

    @Composable
    private fun AddDocument() {
        Box(
            modifier = Modifier
                .size(80.dp)
                .padding(10.dp)
                .background(MainColorDark, shape = CircleShape)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
//                              TODO(Добавить нажатие чтобы вызывалась форма добавления документа)
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

