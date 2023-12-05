package com.example.documentsearch.ui.theme

import com.example.documentsearch.R

data class DropdownItems(var title: String, var icon: Int, var description: String)

val SortAZ = DropdownItems("По алфавиту (с начала)", R.drawable.sort_az_white, "По алфавиту (с начала)")
val SortZA = DropdownItems("По алфавиту (с конца)", R.drawable.sort_za_white, "По алфавиту (с конца)")
val SortDateNew = DropdownItems("По дате (более новое)", R.drawable.sort_date_down_white, "По дате (более новое)")
val SortDateOld = DropdownItems("По дате (более старое)", R.drawable.sort_date_up_white, "По дате (более старое)")

val MessengerMenuCreateGroup = DropdownItems("Создать группу", R.drawable.add_group, "Создать группу")
val MessengerMenuAddFriend = DropdownItems("Добавить контакты", R.drawable.add_user_white, "Добавить контакты")
val MessengerMenuFriends = DropdownItems("Контакты", R.drawable.profile_white, "Контакты")
