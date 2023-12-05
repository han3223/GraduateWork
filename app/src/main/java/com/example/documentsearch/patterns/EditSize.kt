package com.example.documentsearch.patterns

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class EditSize {
    @Composable
    fun getPaddingAfterAnimation(widthContent: Dp, animatedWidth: Dp, afterPadding: Int): Dp {
        return kotlin.math.abs(((1 - ((widthContent - animatedWidth) / (widthContent - 110.dp))).dp * afterPadding).value.toInt()).dp
    }
}