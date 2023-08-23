package com.example.documentsearch.navbar

import com.example.documentsearch.R

sealed class NavigationItem(var route: String, var background: Int, var backgroundFocused: Int, var icon: Int, var title: String) {
    object Documents: NavigationItem("documents", R.drawable.scientific_articles, R.drawable.scientific_articles_focused, R.drawable.page_white, "Document")
    object Messenger: NavigationItem("messenger", R.drawable.messanger, R.drawable.messanger_focused, R.drawable.message_white, "Messenger")
    object AddUser: NavigationItem("add user", R.drawable.scientific_supervisor, R.drawable.scientific_supervisor_focused, R.drawable.add_user_white, "Add User")
    object Profile: NavigationItem("profile", R.drawable.profile, R.drawable.profile_focused, R.drawable.profile_white, "Profile")
}
