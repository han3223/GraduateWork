package com.example.documentsearch.screens.addUser

import android.os.Parcel
import android.os.Parcelable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.api.apiRequests.tag.TagRequestServicesImpl
import com.example.documentsearch.cache.CacheAllUsersProfile
import com.example.documentsearch.cache.CacheProfileTags
import com.example.documentsearch.cache.CacheUserProfile
import com.example.documentsearch.patterns.HeaderFactory
import com.example.documentsearch.screens.document.Filter
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.FILTER
import com.example.documentsearch.ui.theme.HIGHLIGHTING_BOLD_TEXT
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.ORDINARY_TEXT

class AddUserScreen() : Screen, Parcelable {
    private val heightHeader = 160.dp
    private val headerFactory = HeaderFactory()

    private val profileRequestService = ProfileRequestServicesImpl()
    private val tagRequestService = TagRequestServicesImpl()

    private val cacheUserProfile = CacheUserProfile()
    private val cacheAllUsersProfile = CacheAllUsersProfile()
    private val cacheProfileTags = CacheProfileTags()

    private val searchProfile = SearchProfile()
    private val filter = Filter()

    constructor(parcel: Parcel) : this() {
    }

    @Composable
    override fun Content() {
        var isCompleted by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            getAllUsersProfileAndProfileTags()
            isCompleted = true
        }

        if (isCompleted) {
            Box {
                Header()
                Body()
            }
        }
    }

    private suspend fun getAllUsersProfileAndProfileTags() {
        if (cacheAllUsersProfile.getAllUsersProfileFromCache() == null)
            cacheAllUsersProfile.loadAllUsersProfile(profileRequestService.getAllUsersProfile())
        if (cacheProfileTags.getProfileTagsFromCache() == null)
            cacheProfileTags.loadProfileTags(tagRequestService.getProfileTags())
    }

    @Composable
    private fun Header() {
        val configuration = LocalConfiguration.current
        val screenWidthDp = configuration.screenWidthDp

        var isActiveFilter by remember { mutableStateOf(false) }

        Box(modifier = Modifier.zIndex(2f)) {
            val headerModifier = if (isActiveFilter) Modifier
                .align(Alignment.BottomEnd)
                .height(40.dp)
                .width(screenWidthDp.dp - 33.dp)
            else Modifier

            headerFactory.HeaderPrototype(
                height = heightHeader,
                element = if (isActiveFilter) FILTER else "",
                modifier = headerModifier,
                leftEllipseColor = MainColor,
                rightEllipseColor = if (isActiveFilter) MainColorDark else MainColor
            )

            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(heightHeader)
                    .padding(20.dp, 0.dp, 20.dp, 40.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                searchProfile.SearchEngine()
                filter.InActive { isActiveFilter = !isActiveFilter }
            }
        }

        ActiveFilter(isActiveFilter)
    }

    @Composable
    private fun ActiveFilter(isActiveFilter: Boolean) {
        AnimatedVisibility(
            visible = isActiveFilter,
            enter = slideInVertically() + expandVertically(expandFrom = Alignment.Top) + fadeIn(),
            exit = slideOutVertically() + shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(),
            modifier = Modifier
                .zIndex(1f)
                .fillMaxWidth()
        ) {
            filter.ActiveProfile(tags = cacheProfileTags.getProfileTagsFromCache()!!) { }
        }
    }

    @Composable
    private fun Body() {
        val navigator = LocalNavigator.currentOrThrow
        LazyColumn(modifier = Modifier
            .zIndex(0f)
            .padding(top = 127.dp)
            .fillMaxWidth()
            .background(MainColorLight)) {
            cacheAllUsersProfile.getAllUsersProfileFromCache()?.let { usersProfile ->
                items(items = usersProfile) { userProfile ->
                    Row(
                        modifier = Modifier
                            .padding(20.dp, 10.dp, 20.dp, 10.dp)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onTap = {
                                        navigator.push(
                                            ProfileInfo(
                                                userProfile,
                                                cacheUserProfile.getUserFromCache()!!
                                            )
                                        )
                                    },
                                )
                            },
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ProfilePicture()
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            UserName("${userProfile.lastName} ${userProfile.firstName}")
                            if (userProfile.personalInfo != null)
                                UserInfo(userProfile.personalInfo!!)
                        }
                    }
                    Separator()
                }
            }
        }
    }

    @Composable
    private fun ProfilePicture() {
        Box(
            modifier = Modifier
                .size(65.dp)
                .background(AdditionalColor, CircleShape)
        ) {}
    }

    @Composable
    private fun UserName(fullName: String) {
        Text(
            text = fullName,
            style = HIGHLIGHTING_BOLD_TEXT,
        )
    }

    @Composable
    private fun UserInfo(info: String) {
        Text(
            text = if (info.length >= 30) {
                info.substring(0, 30)
            } else info,
            style = ORDINARY_TEXT
        )
    }

    @Composable
    private fun Separator() {
        Spacer(modifier = Modifier.height(1.dp).fillMaxWidth().background(AdditionalColor))
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AddUserScreen> {
        override fun createFromParcel(parcel: Parcel): AddUserScreen {
            return AddUserScreen(parcel)
        }

        override fun newArray(size: Int): Array<AddUserScreen?> {
            return arrayOfNulls(size)
        }
    }
}