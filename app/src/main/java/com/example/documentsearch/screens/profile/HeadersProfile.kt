package com.example.documentsearch.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.documentsearch.navbar.NavigationItem
import com.example.documentsearch.patterns.HeaderFactory
import com.example.documentsearch.ui.theme.HEADING_TEXT
import com.example.documentsearch.ui.theme.TextColor
import com.example.documentsearch.validation.Validation

open class HeadersProfile {
    private val heightHeader = 120.dp
    private val headerFactory = HeaderFactory()
    fun getHeightHeader(): Dp { return heightHeader }

    @Composable
    fun BasicHeader() {
        Box(modifier = Modifier.zIndex(3f).fillMaxWidth()) {
            headerFactory.HeaderPrototype(height = heightHeader)

            Text(
                text = "Профиль",
                style = HEADING_TEXT,
                modifier = Modifier.padding(start = 20.dp, top = 45.dp)
            )
        }
    }

    @Composable
    fun HeaderProfileDataChanged(
        navigationController: NavController,
        title: String,
        value: String,
        onValueChange: (String) -> Unit,
        conditionValidation: Boolean,
    ) {
        Box(modifier = Modifier.zIndex(3f)) {
            headerFactory.HeaderPrototype(height = heightHeader)

            Row(
                modifier = Modifier
                    .padding(20.dp, 45.dp, 20.dp, 0.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = TextColor,
                        modifier = Modifier
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                            ) {
                                navigationController.navigate(NavigationItem.Profile.route)
                            }
                    )
                    Text(
                        text = title,
                        style = HEADING_TEXT
                    )
                }
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = null,
                    tint = if (conditionValidation) TextColor else Color.Gray,
                    modifier = Modifier
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                            enabled = conditionValidation
                        ) {
                            onValueChange(value)
                        }
                )
            }
        }
    }

    @Composable
    fun HeaderProfilePasswordChanged(
        navigationController: NavController,
        newPasswordEnter: String,
        repeatPasswordEnter: String,
        changeValue: (String) -> Unit
    ) {
        Box(modifier = Modifier.zIndex(3f)) {
            headerFactory.HeaderPrototype(height = heightHeader)

            Row(
                modifier = Modifier
                    .padding(20.dp, 45.dp, 20.dp, 0.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = TextColor,
                        modifier = Modifier
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                            ) {
                                navigationController.navigate(NavigationItem.Profile.route)
                            }
                    )
                    Text(text = "Смена пароля", style = HEADING_TEXT)
                }
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = null,
                    tint = if(newPasswordEnter == repeatPasswordEnter && Validation().isValidPassword(newPasswordEnter)) TextColor else Color.Gray,
                    modifier = Modifier
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                            enabled = newPasswordEnter == repeatPasswordEnter && Validation().isValidPassword(newPasswordEnter)
                        ) {
                            changeValue(newPasswordEnter)
                            navigationController.navigate(NavigationItem.Profile.route)
                        }
                )
            }
        }
    }
}