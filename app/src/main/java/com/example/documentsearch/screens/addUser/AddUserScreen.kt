package com.example.documentsearch.screens.addUser

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.documentsearch.navbar.NavigationItem
import com.example.documentsearch.patterns.HeaderFactory
import com.example.documentsearch.prototypes.AnotherUserProfilePrototype
import com.example.documentsearch.prototypes.TagPrototype
import com.example.documentsearch.screens.document.Filter
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.FILTER
import com.example.documentsearch.ui.theme.HIGHLIGHTING_BOLD_TEXT
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.ORDINARY_TEXT

class AddUserScreen(
    navigationController: NavController,
    users: List<AnotherUserProfilePrototype>,
    tags: List<TagPrototype>
) {
    private val navigationController: NavController
    private val users: List<AnotherUserProfilePrototype>
    private val tags: List<TagPrototype>

    private val heightHeader = 160.dp
    private val headerFactory = HeaderFactory()

    init {
        this.navigationController = navigationController
        this.users = users
        this.tags = tags
    }

    @Composable
    fun Screen(onUserChange: (Long) -> Unit) {
        Box {
            Header()
            Body { onUserChange(it) }
        }
    }

    @Composable
    private fun Header() {
        val configuration = LocalConfiguration.current
        val screenWidthDp = configuration.screenWidthDp
        val searchProfile = SearchProfile()
        val filter = Filter()

        var isActiveFilter by remember { mutableStateOf(false) }

        Box(modifier = Modifier.zIndex(2f)) {
            headerFactory.HeaderPrototype(
                height = heightHeader,
                element = if (isActiveFilter) FILTER else "",
                modifier = if (isActiveFilter) Modifier
                    .align(Alignment.BottomEnd)
                    .height(40.dp)
                    .width(screenWidthDp.dp - 33.dp)
                else Modifier,
                leftEllipseColor = MainColor,
                rightEllipseColor = if (isActiveFilter) MainColorDark else MainColor
            )

            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(heightHeader)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                searchProfile.SearchEngine()
                filter.InActive { isActiveFilter = !isActiveFilter }
                Spacer(modifier = Modifier.height(40.dp).fillMaxWidth())
            }
        }

        AnimatedVisibility(
            visible = isActiveFilter,
            enter = slideInVertically() + expandVertically(expandFrom = Alignment.Top) + fadeIn(),
            exit = slideOutVertically() + shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(),
            modifier = Modifier
                .zIndex(1f)
                .fillMaxWidth()
        ) {
            filter.ActiveProfile(tags = tags) { }
        }
    }

    @Composable
    private fun Body(onUserChange: (Long) -> Unit) {
        Column(
            modifier = Modifier
                .zIndex(0f)
                .padding(top = 127.dp)
                .fillMaxWidth()
                .background(MainColorLight)
        ) {
            users.forEach { user ->
                Row(
                    modifier = Modifier
                        .padding(20.dp, 10.dp, 20.dp, 10.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    navigationController.navigate(NavigationItem.ProfileInfo.route)
                                    onUserChange(user.id)
                                },
                            )
                        },
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(65.dp)
                            .background(AdditionalColor, CircleShape)
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "${user.lastName} ${user.firstName}",
                            style = HIGHLIGHTING_BOLD_TEXT,
                        )
                        if (user.personalInfo != null) {
                            Text(
                                text = if (user.personalInfo!!.length >= 30) {
                                    user.personalInfo!!.substring(0, 30)
                                } else user.personalInfo!!,
                                style = ORDINARY_TEXT
                            )
                        }
                    }
                }
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(AdditionalColor)
                )
            }
        }
    }
}