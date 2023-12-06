package com.example.documentsearch.screens.addUser

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import com.example.documentsearch.R
import com.example.documentsearch.api.apiRequests.messenger.MessengersRequestServicesImpl
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.patterns.EditText
import com.example.documentsearch.patterns.HeaderFactory
import com.example.documentsearch.patterns.profile.ProfileFactory
import com.example.documentsearch.prototypes.AddMessengerPrototypeDataBase
import com.example.documentsearch.prototypes.AnotherUserProfilePrototype
import com.example.documentsearch.prototypes.MessengerPrototype
import com.example.documentsearch.prototypes.UserProfilePrototype
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.AdditionalMainColor
import com.example.documentsearch.ui.theme.AdditionalMainColorDark
import com.example.documentsearch.ui.theme.HEADING_TEXT
import com.example.documentsearch.ui.theme.HIGHLIGHTING_BOLD_TEXT
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.ORDINARY_TEXT
import com.example.documentsearch.ui.theme.TextColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class ProfileInfo(
    val anotherProfile: AnotherUserProfilePrototype,
    val userProfile: UserProfilePrototype
) : Screen {
    private val heightHeader = 120.dp
    private val headerFactory = HeaderFactory()
    private val profileFactory = ProfileFactory()

    @Composable
    override fun Content() {
        Box {
            Header()
            Body()
        }
    }

    @Composable
    fun Header() {
        headerFactory.HeaderPrototype(height = heightHeader)
    }

    @Composable
    fun Body() {
        val density = LocalDensity.current
        var widthContent by remember { mutableStateOf(0.dp) }

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(5.dp, 93.dp, 5.dp, 30.dp)
                .zIndex(0f)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(size = 33.dp))
                .background(color = MainColorLight)
        ) {
            Box(
                modifier = Modifier
                    .padding(20.dp, 20.dp, 20.dp, 0.dp)
                    .onSizeChanged { widthContent = with(density) { it.width.toDp() } }
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 0.dp, bottom = 45.dp)
                        .align(Alignment.TopEnd)
                        .size(widthContent, widthContent / 1.5f)
                        .background(color = MainColorDark, shape = RoundedCornerShape(10.dp))
                ) {
                    ProfilePicture()
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 10.dp, bottom = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(modifier = Modifier.width(widthContent - 100.dp)) {
                            FullName("${anotherProfile.lastName} ${anotherProfile.firstName} ${anotherProfile.patronymic}")
                        }
                        Row(
                            modifier = Modifier
                                .width(100.dp)
                                .align(Alignment.Bottom),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Communication()
                            AddUser()
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                ) {
                    PersonalName("@${anotherProfile.personalName}")
                }
            }

            Info()
            Separation()
            NumberPhone()
            Separation()
            Email()
            Separation()

            // Контейнер для тегов пользователя
            if (!anotherProfile.tags.isNullOrEmpty()) {
                Tags(anotherProfile.tags)
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            )
        }
    }

    @Composable
    fun FullName(fullName: String) {
        Text(
            text = fullName,
            style = HEADING_TEXT,
            modifier = Modifier.padding(start = 10.dp)
        )
    }

    @Composable
    fun PersonalName(personalName: String) {
        profileFactory.StandardBlock(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            label = "Пользовательское имя:",
            styleLabel = HIGHLIGHTING_BOLD_TEXT,
            value = personalName,
            styleValue = ORDINARY_TEXT
        )
    }

    @Composable
    fun ProfilePicture() {

    }

    @Composable
    fun Communication() {
        Box(
            modifier = Modifier
                .size(34.dp)
                .background(AdditionalMainColor, CircleShape)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        CoroutineScope(Dispatchers.Main).launch {
                            val request =
                                MessengersRequestServicesImpl().addMessenger(
                                    AddMessengerPrototypeDataBase(
                                        user = userProfile.id!!,
                                        interlocutor = anotherProfile.id
                                    )
                                )
                            if (request != null) {
                                val messenger = MessengerPrototype(
                                    request.id,
                                    anotherProfile,
                                    mutableListOf()
                                )
                                // TODO(Сделать добавление в базу данных и навигацию на переписку)
                            }
                        }
                    })
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.message_white),
                contentDescription = null,
                tint = TextColor,
                modifier = Modifier.size(17.dp)
            )
        }
    }

    @Composable
    fun AddUser() {
        Box(
            modifier = Modifier
                .size(34.dp)
                .background(AdditionalMainColor, CircleShape)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        CoroutineScope(Dispatchers.Main).launch {
                            val profileRequestServices = ProfileRequestServicesImpl()
                            profileRequestServices.addFriendUsingEmail(
                                userProfile.email,
                                anotherProfile.id.toString()
                            )
                        }
                    })
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add_user_white),
                contentDescription = null,
                tint = TextColor,
                modifier = Modifier.size(17.dp)
            )
        }
    }

    @Composable
    private fun Info() {
        profileFactory.StandardBlock(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 10.dp, end = 20.dp),
            label = "Информация:",
            styleLabel = HIGHLIGHTING_BOLD_TEXT,
            value = anotherProfile.personalInfo ?: "",
            styleValue = ORDINARY_TEXT
        )
    }

    @Composable
    private fun NumberPhone() {
        profileFactory.StandardBlock(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 10.dp, end = 20.dp),
            label = "Номер телефона:",
            styleLabel = HIGHLIGHTING_BOLD_TEXT,
            value = EditText().getMaskNumberPhone(anotherProfile.numberPhone),
            styleValue = ORDINARY_TEXT
        )
    }

    @Composable
    private fun Email() {
        profileFactory.StandardBlock(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 10.dp, end = 20.dp),
            label = "Email:",
            styleLabel = HIGHLIGHTING_BOLD_TEXT,
            value = anotherProfile.email,
            styleValue = ORDINARY_TEXT
        )
    }

    @Composable
    private fun Tags(tags: List<Long>) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 10.dp, 20.dp, 10.dp)
                .clip(RoundedCornerShape(18.dp))
                .height(35.dp)
                .background(AdditionalMainColorDark),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(35.dp)
                    .clip(RoundedCornerShape(17.dp))
                    .shadow(
                        40.dp,
                        RoundedCornerShape(14.dp),
                        ambientColor = Color.White,
                        spotColor = Color.White
                    )
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .horizontalScroll(rememberScrollState())
                    .clip(RoundedCornerShape(14.dp))
                    .padding(horizontal = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                tags.forEach {
                    Box(
                        modifier = Modifier
                            .background(
                                color = MainColorDark,
                                shape = RoundedCornerShape(size = 14.dp)
                            )
                            .border(1.dp, Color(0xCC354643), RoundedCornerShape(14.dp))
                    ) {
                        Text(
                            text = it.toString(),
                            style = HIGHLIGHTING_BOLD_TEXT,
                            modifier = Modifier.padding(
                                vertical = 5.dp,
                                horizontal = 10.dp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                }
            }
        }
    }

    @Composable
    private fun Separation() {
        Spacer(
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(AdditionalColor)
        )
    }
}