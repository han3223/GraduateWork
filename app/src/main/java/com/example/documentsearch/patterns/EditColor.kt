package com.example.documentsearch.patterns

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.android.material.animation.ArgbEvaluatorCompat

class EditColor {
    fun evaluateColor(fraction: Float, startColor: Color, endColor: Color): Color {
        return Color(
            ArgbEvaluatorCompat().evaluate(fraction, startColor.toArgb(), endColor.toArgb()).toLong()
        )
    }
}