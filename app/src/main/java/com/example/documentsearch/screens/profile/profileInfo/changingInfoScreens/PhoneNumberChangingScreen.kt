package com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.documentsearch.R
import com.example.documentsearch.patterns.authentication.PhoneInput
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.AdditionalMainColor
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.TextColor
import com.example.documentsearch.validation.ValidationText

@Composable
fun PhoneNumberChangingScreen(
    value: String,
    valueChanged: (String) -> Unit,
    validationText: String,
    label: String,
    conditionValidation: Boolean = true,
    invalidList: List<ValidationText> = listOf()
) {

    Column(
        modifier = Modifier
            .zIndex(2f)
            .fillMaxWidth()
            .height(10000.dp)
            .background(color = MainColorLight),
    ) {
        PhoneInput(
            phoneNumber = value,
            onPhoneNumberChanged = { valueChanged(it) },
            mainBoxModifier = Modifier
                .fillMaxWidth()
                .background(AdditionalMainColor)
                .padding(20.dp, 20.dp, 20.dp, 5.dp),
            textFieldModifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(color = Color.Transparent)
                .onFocusChanged { },
            keyboardActions = KeyboardActions.Default,
            label = label,
            activeTextField = false,
            styleLabel = TextStyle(
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                fontWeight = FontWeight(600),
                color = TextColor,
            ),
            textStyle = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                fontWeight = FontWeight(600),
                color = TextColor,
            ),
            validColor = if (conditionValidation) TextColor else Color.Red,
            invalidList = invalidList
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MainColor)
        )
        Text(
            text = validationText,
            style = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                fontWeight = FontWeight(500),
                color = AdditionalColor,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp, 10.dp, 7.dp, 0.dp)
        )
    }
}