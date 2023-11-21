package com.example.documentsearch.header.messengerScreen

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
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.documentsearch.R
import com.example.documentsearch.navbar.GetShapeFromSVG
import com.example.documentsearch.patterns.HeaderPrototype
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.TextColor
import com.example.documentsearch.ui.theme.messengerMenu

@Composable
fun Header() {
    var isOpenMenu by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .zIndex(3f)
            .fillMaxWidth()
    ) {
        HeaderPrototype(height = 120)
        if (isOpenMenu) {
            Box(modifier = Modifier
                .align(Alignment.BottomStart)
                .width(220.dp)
                .height(79.dp)) {
                GetShapeFromSVG(svgCode = messengerMenu, colorShape = MainColorDark)
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
                        modifier = Modifier.fillMaxSize().pointerInput(Unit) {
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
                    style = TextStyle(
                        fontSize = 19.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                        fontWeight = FontWeight(600),
                        color = TextColor,
                    ),
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
            MessengerMenu()
        }
    }
}