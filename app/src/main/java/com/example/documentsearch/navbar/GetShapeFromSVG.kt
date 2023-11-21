package com.example.documentsearch.navbar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.vector.PathParser


@Composable
fun GetShapeFromSVG(svgCode: String, colorShape: Color, stroke: Stroke? = null) {
    val path = PathParser().parsePathString(svgCode).toPath()

    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val bounds = path.getBounds()
        val scaleX = canvasWidth / bounds.width
        val scaleY = canvasHeight / bounds.height

        scale(scaleX, scaleY, pivot = Offset.Zero) {
            drawPath(
                path = path,
                color = colorShape,
                style = stroke ?: Fill
            )
        }
    }
}