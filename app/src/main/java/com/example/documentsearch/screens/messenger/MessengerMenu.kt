package com.example.documentsearch.screens.messenger

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.documentsearch.ui.theme.AdditionalMainColorDark
import com.example.documentsearch.ui.theme.MessengerMenuAddFriend
import com.example.documentsearch.ui.theme.MessengerMenuCreateGroup
import com.example.documentsearch.ui.theme.MessengerMenuFriends
import com.example.documentsearch.ui.theme.ORDINARY_TEXT
import com.example.documentsearch.ui.theme.SelectedColor
import com.example.documentsearch.ui.theme.TextColor

class MessengerMenu {
    private val listSort =
        listOf(MessengerMenuCreateGroup, MessengerMenuAddFriend, MessengerMenuFriends)

    @Composable
    fun Menu() {
        var activeSortElement by remember { mutableStateOf(MessengerMenuCreateGroup) }

        val sizeMultiplier = remember { Animatable(1f) }
        val columnModifier = Modifier
            .zIndex(1f)
            .width(220.dp)
            .padding(top = 80.dp, bottom = 15.dp)
            .background(AdditionalMainColorDark, RoundedCornerShape(bottomEnd = 20.dp))

        val rowModifier = Modifier
            .padding(20.dp, 10.dp, 10.dp, 15.dp)
            .scale(sizeMultiplier.value)

        Column(modifier = columnModifier) {
            listSort.forEach { sortItem ->
                LaunchedEffect(sizeMultiplier) {
                    sizeMultiplier.animateTo(0.9f, animationSpec = spring())
                    sizeMultiplier.animateTo(1f, animationSpec = spring())
                }

                Row(
                    modifier = rowModifier
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    activeSortElement = sortItem
                                    sizeMultiplier.animateTo(0.95f, animationSpec = spring())
                                    awaitRelease()
                                    sizeMultiplier.animateTo(1f, animationSpec = spring())
                                }
                            )
                    },
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = sortItem.icon),
                        contentDescription = sortItem.description,
                        modifier = Modifier.size(24.dp),
                        tint = if (sortItem != activeSortElement) TextColor else SelectedColor
                    )
                    Text(
                        text = sortItem.title,
                        style = ORDINARY_TEXT
                    )
                }
            }
        }
    }
}