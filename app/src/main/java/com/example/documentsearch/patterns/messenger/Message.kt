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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentsearch.R
import com.example.documentsearch.prototypes.MessagePrototype
import com.example.documentsearch.navbar.GetShapeFromSVG
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.TextColor
import com.example.documentsearch.ui.theme.leftIndicator
import com.example.documentsearch.ui.theme.rightIndicator


@Composable
fun Message(
    message: MessagePrototype,
    isRepeatMyMessage: Boolean,
) {
    // TODO(Разобраться с тем как поставить время, также доделать галочки при прочтении и редектирование сообщения)
    var widthLastLineText by remember { mutableStateOf(0.dp) }

    Box {
        if (!isRepeatMyMessage) {
            Box(
                modifier = Modifier
                    .size(20.dp, 15.dp)
                    .align(if (!message.myMessage) Alignment.BottomStart else Alignment.BottomEnd)
            ) {
                GetShapeFromSVG(
                    svgCode = if (!message.myMessage) leftIndicator else rightIndicator,
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
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                    fontWeight = FontWeight(600),
                    color = TextColor,
                ),
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
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                        fontWeight = FontWeight(600),
                        textAlign = TextAlign.Start,
                        color = TextColor,
                    ),
                )
            }
        }
    }
}