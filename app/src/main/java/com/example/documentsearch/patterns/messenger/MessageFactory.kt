package com.example.documentsearch.patterns.messenger

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import com.example.documentsearch.navbar.SVGFactory
import com.example.documentsearch.prototypes.MessagePrototype
import com.example.documentsearch.ui.theme.LEFT_INDICATOR
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.ORDINARY_TEXT
import com.example.documentsearch.ui.theme.RIGHT_INDICATOR
import com.example.documentsearch.ui.theme.SECONDARY_TEXT

class MessageFactory {
    @Composable
    fun Message(message: MessagePrototype, isRepeatMyMessage: Boolean) {
        // TODO(Разобраться с тем как поставить время, также доделать галочки при прочтении и редактировании сообщения)
        var widthLastLineText by remember { mutableStateOf(0.dp) }

        Box {
            if (!isRepeatMyMessage) {
                Box(
                    modifier = Modifier
                        .size(20.dp, 15.dp)
                        .align(if (!message.myMessage) Alignment.BottomStart else Alignment.BottomEnd)
                ) {
                    val svgFactory = SVGFactory()
                    svgFactory.GetShapeFromSVG(
                        svgCode = if (!message.myMessage) LEFT_INDICATOR else RIGHT_INDICATOR,
                        colorShape = if (!message.myMessage) MainColorLight else MainColorDark
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(
                        start = if (!message.myMessage) 7.dp else 0.dp,
                        end = if (message.myMessage) 7.dp else 0.dp
                    )
                    .background(
                        if (!message.myMessage) MainColorLight else MainColorDark,
                        RoundedCornerShape(15.dp)
                    )
            ) {
                Text(
                    modifier = Modifier.padding(5.dp),
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

                Row(modifier = Modifier.align(Alignment.BottomEnd)) {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = message.time,
                        style = SECONDARY_TEXT,
                    )
                }
            }
        }
    }
}
