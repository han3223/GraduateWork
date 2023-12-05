package com.example.documentsearch.navbar

import com.example.documentsearch.R

sealed class NavigationItem(
    var route: String,
    var icon: Int,
    var title: String,
    var selectionNavbar: String?
) {
    object Documents : NavigationItem(
        "documents",
        R.drawable.page_white,
        "Document",
        "documents"
    )

    object Messenger : NavigationItem(
        "messenger",
        R.drawable.message_white,
        "Messenger",
        "messenger"
    )

    object Communication: NavigationItem(
        "communication",
        R.drawable.message_white,
        "Communication",
        null
    )

    object AddUser : NavigationItem(
        "add user",
        R.drawable.add_user_white,
        "Add User",
        "add user"
    )

    object ProfileInfo : NavigationItem(
        "profile info",
        R.drawable.add_user_white,
        "Profile info",
        "add user"
    )

    object Profile : NavigationItem(
        "profile",
        R.drawable.profile_white,
        "Profile",
        "profile"
    )

    object Login : NavigationItem(
        "login",
        R.drawable.profile_white,
        "Login",
        "profile"
    )

    object Registration : NavigationItem(
        "registration",
        R.drawable.profile_white,
        "Registration",
        "profile"
    )

    object VerificationRegistration : NavigationItem(
        "verify registration",
        R.drawable.profile_white,
        "Verify registration",
        "profile"
    )

    object ForgotPassword : NavigationItem(
        "forgot password",
        R.drawable.profile_white,
        "Forgot password",
        "profile"
    )

    object AnotherForgotPassword : NavigationItem(
        "another forgot password",
        R.drawable.profile_white,
        "Another forgot password",
        "profile"
    )

    object ForgotCode : NavigationItem(
        "forgot code",
        R.drawable.profile_white,
        "forgot code",
        "profile"
    )

    object NewPassword : NavigationItem(
        "new password",
        R.drawable.profile_white,
        "New password",
        "profile"
    )

    object ChangePersonalName : NavigationItem(
        "change personal name",
        R.drawable.profile_white,
        "Change personal name",
        null
    )

    object ChangePersonalInfo : NavigationItem(
        "change personal info",
        R.drawable.profile_white,
        "Change personal info",
        null
    )

    object ChangeNumberPhone : NavigationItem(
        "change personal phone",
        R.drawable.profile_white,
        "Change personal phone",
        null
    )

    object ChangeEmail : NavigationItem(
        "change personal email",
        R.drawable.profile_white,
        "Change personal email",
        null
    )

    object ChangePassword : NavigationItem(
        "change personal password",
        R.drawable.profile_white,
        "Change personal password",
        null
    )
}
