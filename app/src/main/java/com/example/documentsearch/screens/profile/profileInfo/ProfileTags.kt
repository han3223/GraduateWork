package com.example.documentsearch.screens.profile.profileInfo

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.prototypes.ProfilePrototype
import com.example.documentsearch.prototypes.TagPrototype
import com.example.documentsearch.patterns.searchTags.SearchTags
import com.example.documentsearch.patterns.searchTags.SelectedTags
import com.example.documentsearch.patterns.searchTags.Tags
import com.example.documentsearch.ui.theme.MainColorLight
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO(Разобраться с добавлением тегов, сейчас это выглядит ужасно)

/**
 * Функция предназначена для выбора тегов пользователем
 * @param tags Список тегов для пользователя
 * @param profile Профиль пользователя
 */
@SuppressLint("MutableCollectionMutableState", "CoroutineCreationDuringComposition")
@Composable
fun ProfileTags(tags: List<TagPrototype>, profile: ProfilePrototype) {
    var searchTagsValue by remember { mutableStateOf(TextFieldValue("")) } // Значение в поиске
    val selectedTags = remember { mutableStateListOf<TagPrototype>() } // Выбранные теги
    profile.tags?.map { selectedTags.add(tags.first { tag -> tag.id == it }) }

    val queueTags by remember { mutableStateOf(mutableListOf<QueueTags>()) }

    Spacer(modifier = Modifier.height(10.dp))
    Box(
        modifier = Modifier
            .zIndex(2f)
            .fillMaxWidth()
            .heightIn(0.dp, 350.dp)
            .clip(shape = RoundedCornerShape(size = 33.dp))
            .background(color = MainColorLight)
            .padding(horizontal = 20.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.padding(vertical = 10.dp)) {
                    SearchTags(
                        searchTagsValue = searchTagsValue,
                        onSearchTagValueChange = { searchTagsValue = it })
                }
                SelectedTags(
                    selectedTags = selectedTags,
                    onSelectedTagChanged = {
                        queueTags.add(QueueTags(tagId = it.id.toString(), delete = true))
                        CoroutineScope(Dispatchers.Main).launch {
                            val isAdded: Boolean = ProfileRequestServicesImpl().deleteTagUsingEmail(
                                profile.email,
                                it.id.toString()
                            )

                            if (isAdded) {
                                selectedTags.remove(it)
                                println("Удалён: ${it.id}")
                                println("Всего добавлено: ${selectedTags.size}")
                            }
                        }
                    })
                Tags(
                    tags = tags,
                    searchTagsValue = searchTagsValue,
                    selectedTags = selectedTags,
                    onSelectedTagChanged = {
                        queueTags.add(QueueTags(tagId = it.id.toString(), add = true))
                        CoroutineScope(Dispatchers.Main).launch {
                            val isAdded: Boolean = ProfileRequestServicesImpl().addTagUsingEmail(
                                profile.email,
                                it.id.toString()
                            )

                            if (isAdded) {
                                selectedTags.add(it)
                                println("Добавлен: ${it.id}")
                                println("Всего добавлено: ${selectedTags.size}")
                            }
                        }
                    })
            }
        }
    }
}