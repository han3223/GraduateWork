package com.example.documentsearch.patterns.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

class ProfileFactory {
    @Composable
    fun StandardBlock(
        modifier: Modifier,
        label: String,
        styleLabel: TextStyle,
        value: String,
        styleValue: TextStyle,
    ) {
        Column(modifier = modifier) {
            Text(
                text = label,
                style = styleLabel
            )

            Text(
                text = value,
                style = styleValue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 3.dp, 0.dp)
            )
        }
    }
}

