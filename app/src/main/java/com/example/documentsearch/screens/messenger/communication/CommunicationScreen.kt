package com.example.documentsearch.screens.messenger.communication

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import com.example.documentsearch.navbar.SVGFactory
import com.example.documentsearch.patterns.HeaderFactory
import com.example.documentsearch.patterns.authentication.StandardInput
import com.example.documentsearch.patterns.messenger.MessageFactory
import com.example.documentsearch.prototypes.MessengerPrototype
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.HEADER_LEFT
import com.example.documentsearch.ui.theme.HEADER_RIGHT
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.ORDINARY_TEXT
import com.example.documentsearch.ui.theme.TextColor
import kotlinx.coroutines.launch

data class CommunicationScreen(val messenger: MessengerPrototype? = null) : Screen, Parcelable {
    private val heightHeader = 120.dp
    private val headerFactory = HeaderFactory()
    private val messageFactory = MessageFactory()

    constructor(parcel: Parcel) : this()

    @Composable
    override fun Content() {
        val rememberScrollState = rememberScrollState()

        Box {
            Header()
            Body(rememberScrollState)
            BottomBar(rememberScrollState)
        }
    }

    @Composable
    private fun Header() {
        headerFactory.HeaderPrototype(height = heightHeader)
    }

    @Composable
    private fun Body(rememberScrollState: ScrollState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState)
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            messenger!!.listMessage.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .align(if (item.myMessage) Alignment.End else Alignment.Start)
                        .padding(
                            start = if (item.myMessage) 30.dp else 5.dp,
                            end = if (!item.myMessage) 30.dp else 5.dp,
                            top = if (index != 0 && messenger.listMessage[index - 1].myMessage) 2.dp else 10.dp
                        )
                ) {
                    messageFactory.Message(
                        message = item,
                        isRepeatMyMessage = item.myMessage && messenger.listMessage.lastIndex != index && messenger.listMessage[index + 1].myMessage,
                    )
                }
            }
        }
    }

    @Composable
    private fun BottomBar(rememberScrollState: ScrollState) {
        var message by remember { mutableStateOf("") }
        val coroutine = rememberCoroutineScope()

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .rotate(180f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                    Box(modifier = Modifier.size(33.dp).zIndex(2f)) {
                        val svgFactory = SVGFactory()
                        svgFactory.GetShapeFromSVG(svgCode = HEADER_LEFT, colorShape = MainColor)
                    }
                    Box(modifier = Modifier.size(33.dp).zIndex(2f)) {
                        val svgFactory = SVGFactory()
                        svgFactory.GetShapeFromSVG(svgCode = HEADER_RIGHT, colorShape = MainColor)
                    }
            }
            Box(modifier = Modifier.fillMaxWidth().background(MainColor)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 10.dp, 20.dp, 10.dp)
                        .background(AdditionalColor)
                        .align(Alignment.TopCenter),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val standardInput = StandardInput(
                        mainBoxModifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .heightIn(40.dp, 160.dp)
                            .background(AdditionalColor, RoundedCornerShape(20.dp)),
                        label = "",
                        placeholder = "",
                        keyboardOptions = KeyboardOptions.Default,
                        keyboardActions = KeyboardActions.Default,
                        validColor = TextColor,
                        activeTextField = false,
                        textStyle = ORDINARY_TEXT,
                        singleLine = false,
                        textFieldModifier = Modifier.padding(top = 10.dp, start = 3.dp)
                    )
                    standardInput.StandardTextField(
                        value = message,
                        onValueChanged = { message = it },
                        onFocusChange = {},
                        alwaysDisable = true
                    )
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(MainColorDark, CircleShape)
                            .pointerInput(Unit) {
                                detectTapGestures(onTap = {
                                    if (message.isNotEmpty()) {
//                                      TODO(Сделать добавление сообщения)
                                        coroutine.launch {
                                            rememberScrollState.scrollTo(rememberScrollState.maxValue)
                                        }
                                        message = ""
                                    }
                                })
                            }
                    )
                }
            }
        }
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<CommunicationScreen> {
        override fun createFromParcel(parcel: Parcel): CommunicationScreen {
            return CommunicationScreen(parcel)
        }

        override fun newArray(size: Int): Array<CommunicationScreen?> {
            return arrayOfNulls(size)
        }
    }
}