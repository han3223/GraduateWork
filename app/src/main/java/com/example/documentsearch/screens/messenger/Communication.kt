package com.example.documentsearch.screens.messenger

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.documentsearch.prototypes.MessengerPrototype
import com.example.documentsearch.patterns.messenger.Message

@Composable
fun Communication(
    navController: NavHostController,
    messenger: MessengerPrototype,
    rememberScrollState: ScrollState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState)
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp)
        ) {
            messenger.listMessage.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .padding(
                            start = if (item.myMessage) 30.dp else 5.dp,
                            end = if (!item.myMessage) 30.dp else 5.dp,
                            top = if (index != 0 && messenger.listMessage[index - 1].myMessage) 2.dp else 10.dp
                        )
                        .align(if (item.myMessage) Alignment.End else Alignment.Start)
                ) {
                    Message(
                        message = item,
                        isRepeatMyMessage = item.myMessage && messenger.listMessage.lastIndex != index && messenger.listMessage[index + 1].myMessage,
                    )
                }
            }
        }
    }
}