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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.documentsearch.navbar.SVGFactory
import com.example.documentsearch.ui.theme.HEADER_LEFT
import com.example.documentsearch.ui.theme.HEADER_RIGHT
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorDark

class HeaderFactory {
    @Composable
    fun HeaderPrototype(
        height: Dp,
        element: String = "",
        @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
        leftEllipseColor: Color = MainColor,
        rightEllipseColor: Color = MainColor,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height - 33.dp)
                    .background(MainColor)
            ) {
                Box(modifier = modifier) {
                    val svgFactory = SVGFactory()
                    svgFactory.GetShapeFromSVG(svgCode = element, colorShape = MainColorDark)
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
                        val svgFactory = SVGFactory()
                        svgFactory.GetShapeFromSVG(svgCode = HEADER_LEFT, colorShape = leftEllipseColor)
                    }
                }
                // Правый угол
                Box {
                    Box(
                        modifier = Modifier
                            .size(33.dp)
                            .zIndex(2f)
                    ) {
                        val svgFactory = SVGFactory()
                        svgFactory.GetShapeFromSVG(svgCode = HEADER_RIGHT, colorShape = rightEllipseColor)
                    }
                }
            }
        }
    }
}


