package com.example.documentsearch.header.profile

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.documentsearch.R
import com.example.documentsearch.navbar.NavigationItem
import com.example.documentsearch.patterns.HeaderPrototype
import com.example.documentsearch.ui.theme.TextColor

/**
 * Функция нужна для отображения header при смене данных пользователя
 * @param navController Контроллер навигации
 * @param title Заголовок
 * @param value Значение в поле смены данных
 * @param changeValue Обработчик изменения значения в поле
 * @param conditionValidation Пройдена ли валидация
 */
@Composable
fun HeaderProfileDataChanged(
    navController: NavHostController,
    title: String,
    value: String,
    changeValue: (String) -> Unit,
    conditionValidation: Boolean,
) {
    Box(modifier = Modifier.zIndex(3f)) {
        HeaderPrototype(height = 120)
        
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
                            navController.navigate(NavigationItem.Profile.route)
                        }
                )
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 19.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                        fontWeight = FontWeight(600),
                        color = TextColor,
                    ),
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
                        changeValue(value)
                    }
            )
        }
    }
}