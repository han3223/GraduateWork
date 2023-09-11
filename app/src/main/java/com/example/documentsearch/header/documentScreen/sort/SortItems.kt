package com.example.documentsearch.header.documentScreen.sort

import com.example.documentsearch.R

data class SortItems(var title: String, var icon: Int, var description: String)

val SortAZ = SortItems("По алфавиту (с начала)", R.drawable.sort_az_white, "По алфавиту (с начала)")
val SortZA = SortItems("По алфавиту (с конца)", R.drawable.sort_za_white, "По алфавиту (с конца)")
val SortDateNew = SortItems("По дате (более новое)", R.drawable.sort_date_down_white, "По дате (более новое)")
val SortDateOld = SortItems("По дате (более старое)", R.drawable.sort_date_up_white, "По дате (более старое)")
