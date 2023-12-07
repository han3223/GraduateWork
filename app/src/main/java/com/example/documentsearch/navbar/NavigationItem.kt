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

    object AddUser : NavigationItem(
        "add user",
        R.drawable.add_user_white,
        "Add User",
        "add user"
    )

    object Profile : NavigationItem(
        "profile",
        R.drawable.profile_white,
        "Profile",
        "profile"
    )
}
