package com.example.documentsearch.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.example.documentsearch.cache.CacheFactory
import com.example.documentsearch.R
import com.example.documentsearch.prototypes.AnotherUserProfilePrototype
import com.example.documentsearch.prototypes.DocumentWithPercentage
import com.example.documentsearch.prototypes.MessengerPrototype
import com.example.documentsearch.prototypes.TagPrototype
import com.example.documentsearch.prototypes.UserProfilePrototype

val cacheUserProfile = mutableStateOf(CacheFactory<UserProfilePrototype>())
val cacheMessengers = mutableStateOf(CacheFactory<List<MessengerPrototype>>())
val cacheAllUsersProfile = mutableStateOf(CacheFactory<List<AnotherUserProfilePrototype>>())
val cacheProfileTags = mutableStateOf(CacheFactory<List<TagPrototype>>())
val cacheDocumentTags = mutableStateOf(CacheFactory<List<TagPrototype>>())
val cacheDocuments = mutableStateOf(CacheFactory<List<DocumentWithPercentage>>())

const val DOCUMENTS_NAVBAR = "M0 25C0 11.1929 11.1929 0 25 0H80V50H25C11.1929 50 0 38.8071 0 25V25Z"
const val MESSENGER_NAVBAR = "M73.5 0H0V50H128V42C108.481 42 89.7035 28.8923 84.759 9.89665C83.3677 4.5519 79.0229 0 73.5 0Z"
const val ADD_FRIEND_NAVBAR = "M54.5 0H128V50H0V42C19.5191 42 38.2965 28.8923 43.241 9.89665C44.6323 4.5519 48.9771 0 54.5 0Z"
const val PROFILE_NAVBAR = "M80 25C80 11.1929 68.8071 0 55 0H0V50H55C68.8071 50 80 38.8071 80 25V25Z"

const val HEADER_LEFT = "M0 38C0 18 14 0 44.5 0H0V38Z"
const val HEADER_RIGHT = "M44.5 38C44.5 18 30.5 0 0 0H44.5V38Z"

const val SORT = "M191.653 0H0V50.5H287C287 46.65 284.472 43.2711 280.762 42.2442C257.273 35.7436 217.172 25.2794 205.36 9.56713C201.961 5.04579 197.31 0 191.653 0Z"
const val FILTER = "M256.749 0H390V50H0C0 50 29.5 47.5 93 42C149.577 35.4719 226.887 28.6835 245.91 6.22845C248.705 2.92887 252.425 0 256.749 0Z"

const val LEFT_TOP = "M15 15.0001V0.00012207C6.71574 0.000145903 0 6.71588 0 15.0001H15Z"
const val RIGHT_TOP = "M0 15.0001V0.00012207C8.28426 0.000145903 15 6.71588 15 15.0001H0Z"
const val RIGHT_BOTTOM = "M0 0.000108719V15.0001C8.28426 15.0001 15 8.28437 15 0.000108719H0Z"
const val LEFT_BOTTOM = "M15 0.000108719V15.0001C6.71574 15.0001 0 8.28437 0 0.000108719H15Z"
const val LEFT_INDICATOR = "M23 12V0H8C8 9.5 2.5 7.5 0 12H23Z"
const val RIGHT_INDICATOR = "M0 12V0H15C15 9.5 20.5 7.5 23 12H0Z"

const val MESSENGER_MENU = "M0 89.6688C0 70.1756 14 52.6317 44.5 52.6317H287C287 46.7049 282.035 44.0001 276.108 43.9998C85.7523 43.9892 66.2308 43.4112 64.2288 12.0153C63.807 5.40136 58.6293 4.93416e-06 52.0019 2.66695e-06L0 8.90779e-06V89.6688Z"

val MAXIMUM_TEXT = TextStyle(
    fontSize = 25.sp,
    fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
    fontWeight = FontWeight(600),
    textAlign = TextAlign.Start,
    color = TextColor,
)

val HEADING_TEXT = TextStyle(
    fontSize = 19.sp,
    fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
    fontWeight = FontWeight(600),
    textAlign = TextAlign.Start,
    color = TextColor,
)

val ORDINARY_TEXT = TextStyle(
    fontSize = 14.sp,
    fontFamily = FontFamily(Font(R.font.montserrat_medium)),
    fontWeight = FontWeight(600),
    textAlign = TextAlign.Start,
    color = TextColor,
)

val SECONDARY_TEXT = TextStyle(
    fontSize = 12.sp,
    fontFamily = FontFamily(Font(R.font.montserrat_medium)),
    fontWeight = FontWeight(500),
    textAlign = TextAlign.Start,
    color = AdditionalColor
)

val HIGHLIGHTING_BOLD_TEXT = TextStyle(
    fontSize = 14.sp,
    fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
    fontWeight = FontWeight(600),
    textAlign = TextAlign.Start,
    color = TextColor,
)

val HIGHLIGHTING_CURSIVE_TEXT = TextStyle(
    fontSize = 14.sp,
    fontFamily = FontFamily(Font(R.font.montserrat_italic)),
    fontWeight = FontWeight(600),
    textAlign = TextAlign.Start,
    color = TextColor,
)

val HIGHLIGHTING_UNDERLINE_TEXT = TextStyle(
    fontSize = 14.sp,
    fontFamily = FontFamily(Font(R.font.montserrat_italic)),
    fontWeight = FontWeight(600),
    textAlign = TextAlign.Start,
    color = TextColor,
    textDecoration = TextDecoration.Underline
)