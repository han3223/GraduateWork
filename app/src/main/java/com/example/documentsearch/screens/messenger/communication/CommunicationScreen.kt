package com.example.documentsearch.screens.messenger.communication

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.documentsearch.api.SocketManager
import com.example.documentsearch.api.apiRequests.messenger.MessengersRequestServicesImpl
import com.example.documentsearch.navbar.SVGFactory
import com.example.documentsearch.patterns.HeaderFactory
import com.example.documentsearch.patterns.authentication.StandardInput
import com.example.documentsearch.patterns.messenger.MessageFactory
import com.example.documentsearch.prototypes.ChatData
import com.example.documentsearch.prototypes.MessagePrototype
import com.example.documentsearch.prototypes.UserProfilePrototype
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.BackgroundColor
import com.example.documentsearch.ui.theme.EnumCategories
import com.example.documentsearch.ui.theme.HEADER_LEFT
import com.example.documentsearch.ui.theme.HEADER_RIGHT
import com.example.documentsearch.ui.theme.HIGHLIGHTING_BOLD_TEXT
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.ORDINARY_TEXT
import com.example.documentsearch.ui.theme.TextColor
import com.example.documentsearch.ui.theme.cacheUserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.LocalTime

val selectedMessenger = mutableStateOf<ChatData?>(null)
val selectedUser = mutableStateOf<UserProfilePrototype?>(null)

@Suppress("UNREACHABLE_CODE")
class CommunicationScreen : Screen, Parcelable {
    private val heightHeader = 120.dp
    private val headerFactory = HeaderFactory()
    private val messageFactory = MessageFactory()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        val rememberScrollState = rememberLazyListState()
        var messages = remember {
            if (selectedMessenger.value?.messages == null)
                mutableStateListOf()
            else
                selectedMessenger.value?.messages?.toMutableStateList()
        }

        LaunchedEffect(selectedMessenger.value) {
            messages = selectedMessenger.value!!.messages.toMutableStateList()
        }

        Scaffold(
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BackgroundColor)
                ) {
                    Header()
                    Body(messages, rememberScrollState)
                }
            },
            bottomBar = {
                BottomBar(
                    rememberScrollState = rememberScrollState,
                    onMessageChange = {}
                )
            })
    }

    @Composable
    private fun Header() {
        headerFactory.HeaderPrototype(height = heightHeader)
        val navigator = LocalNavigator.currentOrThrow

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(heightHeader)
                .padding(20.dp, 0.dp, 20.dp, 40.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = TextColor,
                modifier = Modifier.padding(bottom = 7.dp)
                    .clickable {
                        navigator.pop()
                        Log.i("test", "test")
                    }
            )
            Box(modifier = Modifier.padding(start = 20.dp).size(40.dp).background(AdditionalColor, CircleShape))
            Text(
                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
                text = "${selectedUser.value?.lastName} ${selectedUser.value?.firstName}",
                style = HIGHLIGHTING_BOLD_TEXT
            )
        }
    }

    @Composable
    private fun Body(
        messages: SnapshotStateList<MessagePrototype>?,
        rememberScrollState: LazyListState
    ) {
        val user = cacheUserProfile.value

        LazyColumn(
            state = rememberScrollState,
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.Bottom,
        ) {
            if (messages != null) {
                itemsIndexed(items = messages) { index, item ->
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .align(if (item.user_id == user?.id) Alignment.CenterEnd else Alignment.CenterStart)
                                .padding(
                                    start = if (item.user_id == user?.id) 30.dp else 5.dp,
                                    end = if (item.user_id == user?.id) 5.dp else 30.dp,
                                    top = if (index != 0 && messages[index - 1].user_id == user?.id) 2.dp else 10.dp
                                )
                        ) {
                            messageFactory.Message(
                                message = item,
                                isRepeatMyMessage = item.user_id == user?.id && messages.lastIndex != index && messages[index + 1].user_id == user.id,
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun BottomBar(
        rememberScrollState: LazyListState,
        onMessageChange: (MessagePrototype) -> Unit
    ) {
        var textMessage by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
        ) {
            Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .rotate(180f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .size(33.dp)
                            .zIndex(2f)
                    ) {
                        val svgFactory = SVGFactory()
                        svgFactory.GetShapeFromSVG(svgCode = HEADER_LEFT, colorShape = MainColor)
                    }
                    Box(
                        modifier = Modifier
                            .size(33.dp)
                            .zIndex(2f)
                    ) {
                        val svgFactory = SVGFactory()
                        svgFactory.GetShapeFromSVG(svgCode = HEADER_RIGHT, colorShape = MainColor)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MainColor)
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp, 10.dp),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        val standardInput = StandardInput(
                            mainBoxModifier = Modifier,
                            label = "",
                            placeholder = "",
                            keyboardOptions = KeyboardOptions.Default,
                            keyboardActions = KeyboardActions.Default,
                            validColor = TextColor,
                            activeTextField = false,
                            textStyle = ORDINARY_TEXT,
                            singleLine = false,
                            textFieldModifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .background(AdditionalColor, RoundedCornerShape(15.dp))
                        )
                        standardInput.StandardTextField(
                            modifier = Modifier
                                .heightIn(30.dp, 150.dp)
                                .padding(5.dp),
                            value = textMessage,
                            onValueChanged = { textMessage = it },
                            onFocusChange = {},
                            alwaysDisable = true
                        )
                        SendButton(
                            textMessage = textMessage,
                            rememberScrollState = rememberScrollState,
                            onMessageChange = {
                                onMessageChange(it)
                                textMessage = ""
                            })
                    }
                }
            }
        }
    }

    @Composable
    private fun SendButton(
        textMessage: String,
        rememberScrollState: LazyListState,
        onMessageChange: (MessagePrototype) -> Unit
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .background(Color.Black, CircleShape)
                .clickable {
                    if (textMessage.isNotEmpty()) {
                        CoroutineScope(Dispatchers.Main).launch {
                            val messenger = selectedMessenger.value
                            if (messenger == null) {
                                val resultAddMessenger = createMessenger()
                                if (resultAddMessenger != null) {
                                    selectedMessenger.value = resultAddMessenger

                                    SocketManager.connectCommunicationRoom(resultAddMessenger.id!!)
                                    val message = createMessage(textMessage, resultAddMessenger.id)
                                    if (message != null) {
                                        onMessageChange(message)
                                        postMessage(message)
                                    }
                                }
                            } else {
                                val message = createMessage(textMessage, messenger.id!!)
                                if (message != null) {
                                    onMessageChange(message)
                                    postMessage(message)
                                }
                            }

                            rememberScrollState.scrollToItem(0)
                        }
                    }
                }
        )
    }

    private fun createMessage(textMessage: String, messengerId: Long): MessagePrototype? {
        val user = cacheUserProfile.value
        return if (user != null) {
            MessagePrototype(
                date = LocalDate.now().toString(),
                time = LocalTime.now().toString(),
                message = textMessage,
                messenger_id = messengerId,
                user_id = user.id!!
            )
        } else null
    }

    private suspend fun createMessenger(): ChatData? {
        val messengersRequestServicesImpl = MessengersRequestServicesImpl()

        val user = cacheUserProfile.value
        val interlocutor = selectedUser.value
        return if (user != null && interlocutor != null) {
            messengersRequestServicesImpl.addMessenger(
                ChatData(participants = listOf(user, interlocutor))
            )
        } else
            null
    }

    private fun postMessage(message: MessagePrototype) {
        SocketManager.sendMessage(Json.encodeToString(message))
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
    }

    companion object CREATOR : Parcelable.Creator<CommunicationScreen> {
        override fun createFromParcel(parcel: Parcel): CommunicationScreen {
            return CommunicationScreen()
        }

        override fun newArray(size: Int): Array<CommunicationScreen?> {
            return arrayOfNulls(size)
        }
    }
}