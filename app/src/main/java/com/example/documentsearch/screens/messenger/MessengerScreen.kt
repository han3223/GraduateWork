package com.example.documentsearch.screens.messenger

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.documentsearch.R
import com.example.documentsearch.navbar.NavigationItem
import com.example.documentsearch.navbar.SVGFactory
import com.example.documentsearch.patterns.HeaderFactory
import com.example.documentsearch.prototypes.MessengerPrototype
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.HEADING_TEXT
import com.example.documentsearch.ui.theme.HIGHLIGHTING_BOLD_TEXT
import com.example.documentsearch.ui.theme.MESSENGER_MENU
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.SECONDARY_TEXT
import com.example.documentsearch.ui.theme.TextColor

class MessengerScreen(
    navigationController: NavController,
    messengers: MutableList<MessengerPrototype>
) {
    private val navigationController: NavController
    private val messengers: MutableList<MessengerPrototype>

    private val heightHeader = 120.dp
    private val headerFactory = HeaderFactory()

    init {
        this.navigationController = navigationController
        this.messengers = messengers
    }

    @Composable
    fun Screen(onMessengerChange: (MessengerPrototype) -> Unit) {
        Box {
            Header()
            Body { onMessengerChange(it) }
        }
    }

    @Composable
    private fun Header() {
        var isOpenMenu by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .zIndex(1f)
                .fillMaxWidth()
        ) {
            headerFactory.HeaderPrototype(heightHeader)
            if (isOpenMenu) {
                Box(modifier = Modifier
                    .align(Alignment.BottomStart)
                    .width(220.dp)
                    .height(79.dp)) {
                    val svgFactory = SVGFactory()
                    svgFactory.GetShapeFromSVG(svgCode = MESSENGER_MENU, colorShape = MainColorDark)
                }
            }
            Row(
                modifier = Modifier
                    .padding(20.dp, 45.dp, 20.dp, 0.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(modifier = Modifier.size(25.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.menu),
                            contentDescription = null,
                            tint = TextColor,
                            modifier = Modifier
                                .fillMaxSize()
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onTap = {
                                            isOpenMenu = !isOpenMenu
                                        }
                                    )
                                }
                        )
                    }
                    Text(
                        text = "Мессенджер",
                        style = HEADING_TEXT
                    )
                }
                Box(
                    modifier = Modifier
                        .size(30.dp, 30.dp)
                        .background(color = MainColorDark, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = TextColor,
                    )
                }
            }
        }

        Box {
            AnimatedVisibility(
                visible = isOpenMenu,
                enter = slideInVertically() + expandVertically(expandFrom = Alignment.Top) + fadeIn(),
                exit = slideOutVertically() + shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(),
                modifier = Modifier.zIndex(1f)
            ) {
                MessengerMenu().Menu()
            }
        }
    }

    @Composable
    private fun Body(onMessengerChange: (MessengerPrototype) -> Unit) {
        Column(
            modifier = Modifier
                .zIndex(0f)
                .fillMaxWidth()
                .background(MainColorLight)
        ) {
            messengers.forEach { messenger ->
                if (messenger.listMessage.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .padding(20.dp, 10.dp, 20.dp, 10.dp)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onTap = {
                                        navigationController.navigate(NavigationItem.Communication.route)
                                        onMessengerChange(messenger)
                                    },
                                )
                            },
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier
                            .size(65.dp)
                            .background(AdditionalColor, CircleShape))
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = "${messenger.interlocutor.lastName} ${messenger.interlocutor.firstName}",
                                style = HIGHLIGHTING_BOLD_TEXT
                            )
                            Text(
                                text = "${messenger.listMessage.last().message.substring(0..30)}...",
                                style = SECONDARY_TEXT
                            )
                        }
                    }
                    Spacer(modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(AdditionalColor))
                }
            }
        }
    }
}