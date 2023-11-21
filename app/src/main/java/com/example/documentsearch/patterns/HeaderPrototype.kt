package com.example.documentsearch.patterns

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.documentsearch.navbar.GetShapeFromSVG
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.headerLeft
import com.example.documentsearch.ui.theme.headerRight

@Composable
fun HeaderPrototype(
    height: Int,
    element: String = "",
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    leftEllipseColor: Color = MainColor,
    rightEllipseColor: Color = MainColor,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height((height - 33).dp)
                .background(MainColor)
        ) {
            Box(modifier = modifier) {
                GetShapeFromSVG(svgCode = element, colorShape = MainColorDark)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Левый угол
            Box {
                Box(
                    modifier = Modifier
                        .size(33.dp)
                        .zIndex(2f)
                ) {
                    GetShapeFromSVG(svgCode = headerLeft, colorShape = leftEllipseColor)
                }
                // Тень
                Box(
                    modifier = Modifier
                        .size(33.dp)
                        .zIndex(1f)
                ) {
                    GetShapeFromSVG(
                        svgCode = "M44.5 1C14 1 0 19 0 39",
                        colorShape = Color(0xD9000000),
                        stroke = Stroke(width = 2f)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .height(1.5.dp)
                    .weight(1f)
                    .background(Color(0xD9000000))
            )
            // Правый угол
            Box {
                Box(
                    modifier = Modifier
                        .size(33.dp)
                        .zIndex(2f)
                ) {
                    GetShapeFromSVG(svgCode = headerRight, colorShape = rightEllipseColor)
                }
                // Тень
                Box(
                    modifier = Modifier
                        .size(33.dp)
                        .zIndex(1f)
                ) {
                    GetShapeFromSVG(
                        svgCode = "M0 1C30.5 1 44.5 19 44.5 39",
                        colorShape = Color(0xD9000000),
                        stroke = Stroke(width = 2f)
                    )
                }
            }

        }
    }
}