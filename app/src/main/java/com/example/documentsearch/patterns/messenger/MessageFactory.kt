package com.example.documentsearch.patterns.messenger

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.documentsearch.navbar.SVGFactory
import com.example.documentsearch.prototypes.MessagePrototype
import com.example.documentsearch.ui.theme.LEFT_INDICATOR
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.ORDINARY_TEXT
import com.example.documentsearch.ui.theme.RIGHT_INDICATOR
import com.example.documentsearch.ui.theme.SECONDARY_TEXT
import com.example.documentsearch.ui.theme.cacheUserProfile

class MessageFactory {
    @Composable
    fun Message(message: MessagePrototype, isRepeatMyMessage: Boolean) {
        val user = cacheUserProfile.value
        var widthLastLineText by remember { mutableStateOf(0.dp) }

        Box {
            if (!isRepeatMyMessage) {
                Box(
                    modifier = Modifier
                        .size(20.dp, 15.dp)
                        .align(if (message.user_id != user?.id) Alignment.BottomStart else Alignment.BottomEnd)
                ) {
                    val svgFactory = SVGFactory()
                    svgFactory.GetShapeFromSVG(
                        svgCode = if (message.user_id != user?.id) LEFT_INDICATOR else RIGHT_INDICATOR,
                        colorShape = if (message.user_id != user?.id) MainColorLight else MainColorDark
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(
                        start = if (message.user_id != user?.id) 7.dp else 0.dp,
                        end = if (message.user_id == user?.id) 7.dp else 0.dp
                    )
                    .background(
                        if (message.user_id == user?.id) MainColorDark else MainColorLight,
                        RoundedCornerShape(15.dp)
                    )
            ) {
                Text(
                    modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 5.dp),
                    text = message.message,
                    style = ORDINARY_TEXT,
                    onTextLayout = {
                        val lastLineIndex = it.lineCount - 1
                        val lastLineStartOffset = it.getLineStart(lastLineIndex)
                        val lastLineEndOffset = it.getLineEnd(lastLineIndex)
                        val lastLineLength = lastLineEndOffset - lastLineStartOffset

                        widthLastLineText = (9.6 * lastLineLength).dp
                    }
                )

                Text(
                    modifier = Modifier.align(Alignment.End).padding(10.dp, 0.dp, 10.dp, 5.dp),
                    text = message.time.substringBeforeLast(':'),
                    style = SECONDARY_TEXT.merge(TextStyle(textAlign = TextAlign.End)),
                )
            }
        }
    }
}
