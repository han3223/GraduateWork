//package com.example.documentsearch
//
//import com.example.documentsearch.api.apiRequests.profile.post.AdditionsServiceInProfile
//import com.example.documentsearch.prototypes.UserProfilePrototype
//import junit.framework.TestCase.assertEquals
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import org.junit.Test
//
//class TestProfile {
//    private val additionsServiceInProfile = AdditionsServiceInProfile()
//
//    @Test
//    fun add() {
//        CoroutineScope().launch {
//            val profile = UserProfilePrototype(
//                lastName = "Петров",
//                firstName = "Пётр",
//                patronymic = "Петрович",
//                phoneNumber = "81234567890",
//                email = "petr12@gmail.com",
//                password = "Qwerty123"
//            )
//
//            val result = additionsServiceInProfile.addProfile(profile)
//            assertEquals(result != null, true)
//        }
//    }
//}