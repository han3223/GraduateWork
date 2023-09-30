package com.example.documentsearch.navbar

import com.example.documentsearch.R

/**
 * Класс предоставляет список элементов для navbar
 */
sealed class NavigationItem(
    var route: String,
    var background: Int,
    var backgroundActive: Int,
    var icon: Int,
    var title: String,
) {
    object Documents : NavigationItem(
        "documents",
        R.drawable.scientific_articles,
        R.drawable.scientific_articles_focused,
        R.drawable.page_white,
        "Document",
    )

    object Messenger : NavigationItem(
        "messenger",
        R.drawable.messanger,
        R.drawable.messanger_focused,
        R.drawable.message_white,
        "Messenger",
    )

    object AddUser : NavigationItem(
        "add user",
        R.drawable.scientific_supervisor,
        R.drawable.scientific_supervisor_focused,
        R.drawable.add_user_white,
        "Add User"
    )

    object Profile : NavigationItem(
        "profile",
        R.drawable.profile,
        R.drawable.profile_focused,
        R.drawable.profile_white,
        "Profile"
    )

    object Login : NavigationItem(
        "login",
        R.drawable.profile,
        R.drawable.profile_focused,
        R.drawable.profile_white,
        "Login"
    )

    object Registration : NavigationItem(
        "registration",
        R.drawable.profile,
        R.drawable.profile_focused,
        R.drawable.profile_white,
        "Registration"
    )

    object VerificationRegistration : NavigationItem(
        "verify registration",
        R.drawable.profile,
        R.drawable.profile_focused,
        R.drawable.profile_white,
        "Verify registration"
    )

    object ForgotPassword : NavigationItem(
        "forgot password",
        R.drawable.profile,
        R.drawable.profile_focused,
        R.drawable.profile_white,
        "Forgot password"
    )

    object AnotherForgotPassword : NavigationItem(
        "another forgot password",
        R.drawable.profile,
        R.drawable.profile_focused,
        R.drawable.profile_white,
        "Another forgot password"
    )

    object ForgotCode : NavigationItem(
        "forgot code",
        R.drawable.profile,
        R.drawable.profile_focused,
        R.drawable.profile_white,
        "forgot code"
    )

    object NewPassword : NavigationItem(
        "new password",
        R.drawable.profile,
        R.drawable.profile_focused,
        R.drawable.profile_white,
        "New password"
    )

    object ChangePersonalName : NavigationItem(
        "change personal name",
        R.drawable.profile,
        R.drawable.profile_focused,
        R.drawable.profile_white,
        "Change personal name"
    )

    object ChangePersonalInfo : NavigationItem(
        "change personal info",
        R.drawable.profile,
        R.drawable.profile_focused,
        R.drawable.profile_white,
        "Change personal info"
    )

    object ChangeNumberPhone : NavigationItem(
        "change personal phone",
        R.drawable.profile,
        R.drawable.profile_focused,
        R.drawable.profile_white,
        "Change personal phone"
    )

    object ChangeEmail : NavigationItem(
        "change personal email",
        R.drawable.profile,
        R.drawable.profile_focused,
        R.drawable.profile_white,
        "Change personal email"
    )

    object ChangePassword : NavigationItem(
        "change personal password",
        R.drawable.profile,
        R.drawable.profile_focused,
        R.drawable.profile_white,
        "Change personal password"
    )
}
