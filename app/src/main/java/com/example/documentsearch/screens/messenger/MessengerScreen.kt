package com.example.documentsearch.screens.messenger

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.documentsearch.navbar.SVGFactory
import com.example.documentsearch.patterns.HeaderFactory
import com.example.documentsearch.prototypes.ChatData
import com.example.documentsearch.screens.messenger.communication.CommunicationScreen
import com.example.documentsearch.screens.messenger.communication.selectedMessenger
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.HEADING_TEXT
import com.example.documentsearch.ui.theme.HIGHLIGHTING_BOLD_TEXT
import com.example.documentsearch.ui.theme.MESSENGER_MENU
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.SECONDARY_TEXT
import com.example.documentsearch.ui.theme.TextColor
import com.example.documentsearch.ui.theme.cacheMessengers
import com.example.documentsearch.ui.theme.cacheUserProfile

class MessengerScreen() : Screen, Parcelable {
    private val heightHeader = 120.dp
    private val headerFactory = HeaderFactory()

    constructor(parcel: Parcel) : this()


    @Composable
    override fun Content() {
        Box {
            Header()
            Body()
        }
    }

    @Composable
    private fun Header() {
        var isOpenMenu by remember { mutableStateOf(false) }

        Box(modifier = Modifier
            .zIndex(2f)
            .fillMaxWidth()) {
            headerFactory.HeaderPrototype(height = heightHeader)
            if (isOpenMenu) {
                Box(modifier = Modifier
                    .align(Alignment.BottomStart)
                    .width(220.dp)
                    .height(79.dp)) {
                    val svgFactory = SVGFactory()
                    svgFactory.GetShapeFromSVG(svgCode = MESSENGER_MENU, colorShape = MainColorDark)
                }
            }
            HeaderItems { isOpenMenu = !isOpenMenu }
        }
        ActiveMenu(isOpenMenu)
    }

    @Composable
    private fun HeaderItems(onOpenMenu: () -> Unit) {
        Row(
            modifier = Modifier
                .padding(20.dp, 45.dp, 20.dp, 0.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MenuAndTitle { onOpenMenu() }
            Search()
        }
    }

    @Composable
    private fun MenuAndTitle(onOpenMenu: () -> Unit) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
//            Box(modifier = Modifier.size(25.dp)) {
//                Icon(
//                    painter = painterResource(id = R.drawable.menu),
//                    contentDescription = null,
//                    tint = TextColor,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .pointerInput(Unit) {
//                            detectTapGestures(
//                                onTap = {
//                                    if (isClickBlock.value)
//                                        onOpenMenu()
//                                }
//                            )
//                        }
//                )
//            }
            Text(
                text = "Мессенджер",
                style = HEADING_TEXT
            )
        }
    }

    @Composable
    private fun Search() {
        Box(
            modifier = Modifier
                .size(30.dp, 30.dp)
                .background(color = MainColorDark, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = TextColor)
        }
    }

    @Composable
    private fun ActiveMenu(isOpenMenu: Boolean) {
        AnimatedVisibility(
            visible = isOpenMenu,
            enter = slideInVertically() + expandVertically(expandFrom = Alignment.Top) + fadeIn(),
            exit = slideOutVertically() + shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(),
            modifier = Modifier.zIndex(1f)
        ) {
            MessengerMenu().Menu()
        }
    }

    @Composable
    private fun Body() {
        var messenger by remember { mutableStateOf(cacheMessengers.value) }

        LaunchedEffect(cacheMessengers.value) {
            messenger = cacheMessengers.value
            Log.i("Проверка", messenger.toString())
        }

        LazyColumn(
            modifier = Modifier
                .zIndex(0f)
                .padding(top = heightHeader - 33.dp)
                .fillMaxWidth()
                .background(MainColorLight)
        ) {
            items(messenger) { messenger ->
                if (messenger.messages.isNotEmpty()) {
                    Communication(messenger)
                    Spacer(modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(AdditionalColor))
                }
            }
        }
    }

    @Composable
    private fun Communication(messenger: ChatData) {
        val navigator = LocalNavigator.currentOrThrow
        val interlocutor = messenger.participants.first { it.id != cacheUserProfile.value!!.id}
        Row(
            modifier = Modifier
                .padding(20.dp, 10.dp, 20.dp, 10.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        selectedMessenger.value = messenger
                        navigator.push(CommunicationScreen())
                    })
                },
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .size(65.dp)
                .background(AdditionalColor, CircleShape))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "${interlocutor.lastName} ${interlocutor.firstName}",
                    style = HIGHLIGHTING_BOLD_TEXT
                )
                Text(
                    text = "${messenger.messages.last().message}",
                    style = SECONDARY_TEXT
                )
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MessengerScreen> {
        override fun createFromParcel(parcel: Parcel): MessengerScreen {
            return MessengerScreen(parcel)
        }

        override fun newArray(size: Int): Array<MessengerScreen?> {
            return arrayOfNulls(size)
        }
    }
}