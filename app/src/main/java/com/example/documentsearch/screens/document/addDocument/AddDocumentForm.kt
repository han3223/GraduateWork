package com.example.documentsearch.screens.document.addDocument

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.documentsearch.R
import com.example.documentsearch.api.apiRequests.document.DocumentRequestServicesImpl
import com.example.documentsearch.api.apiRequests.file.FileRequestServicesImpl
import com.example.documentsearch.navbar.StatusAddDocumentForm
import com.example.documentsearch.patterns.SearchTag
import com.example.documentsearch.patterns.authentication.StandardInput
import com.example.documentsearch.prototypes.DocumentPrototype
import com.example.documentsearch.prototypes.TagPrototype
import com.example.documentsearch.screens.document.Categories
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.EnumCategories
import com.example.documentsearch.ui.theme.HEADING_TEXT
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.ORDINARY_TEXT
import com.example.documentsearch.ui.theme.TextColor
import com.example.documentsearch.ui.theme.cacheDocumentTags
import com.example.documentsearch.ui.theme.cacheUserProfile
import com.example.documentsearch.ui.theme.isClickBlock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Base64


class AddDocumentForm {
    data class DpSize(val width: Dp, val height: Dp) {
        companion object {
            val Zero = DpSize(0.dp, 500.dp)
        }
    }

    @Composable
    fun Content(statusAddDocumentForm: String, onStatusAddDocumentFormChange: (String) -> Unit) {
        var boxSize by remember { mutableStateOf(DpSize.Zero) }
        val density = LocalDensity.current

        var offsetY by remember { mutableStateOf(boxSize.height) }
        val animatedOffsetY by animateDpAsState(
            targetValue =
                when (statusAddDocumentForm) {
                    StatusAddDocumentForm.OPEN.name -> 30.dp
                    StatusAddDocumentForm.CLOSE.name -> boxSize.height
                    else -> offsetY
                },
            animationSpec = spring(),
            label = ""
        ) {
            if (statusAddDocumentForm == StatusAddDocumentForm.OPEN.name)
                onStatusAddDocumentFormChange(StatusAddDocumentForm.NEUTRAL.name)
        }

        isClickBlock.value = statusAddDocumentForm == StatusAddDocumentForm.CLOSE.name

        if (statusAddDocumentForm == StatusAddDocumentForm.OPEN.name)
            offsetY = 30.dp
        else if (statusAddDocumentForm == StatusAddDocumentForm.CLOSE.name)
            offsetY = boxSize.height

        Box(
            modifier = Modifier
                .offset(y = animatedOffsetY)
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    boxSize = DpSize(
                        width = with(density) { coordinates.size.width.toDp() },
                        height = with(density) { coordinates.size.height.toDp() }
                    )
                }
                .background(
                    color = MainColorDark,
                    shape = RoundedCornerShape(33.dp, 33.dp, 0.dp, 0.dp)
                )
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { _: PointerInputChange, dragAmount: Offset ->
                            if (offsetY >= 30.dp)
                                offsetY += dragAmount.y.toDp()
                        },
                        onDragEnd = {
                            if (offsetY > 50.dp)
                                onStatusAddDocumentFormChange(StatusAddDocumentForm.CLOSE.name)
                            else
                                onStatusAddDocumentFormChange(StatusAddDocumentForm.OPEN.name)
                        }
                    )
                }
        ) {
            FormContainer { onStatusAddDocumentFormChange(StatusAddDocumentForm.CLOSE.name) }
        }
    }

    @Composable
    private fun FormContainer(onClickButton: () -> Unit) {
        var title by remember { mutableStateOf("") }
        var documentInString by remember { mutableStateOf("") }
        var category by remember { mutableStateOf(EnumCategories.NOT_SELECTED.category) }
        var selectedTags = remember { mutableStateListOf<TagPrototype>() }

        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ClosingLine()
            Spacer(modifier = Modifier.height(20.dp))
            TitleDocument(title = title) { title = it }
            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                Box(modifier = Modifier.weight(0.5f)) { AddFileButton { documentInString = it } }
                Box(modifier = Modifier.weight(0.5f)) { SelectCategory(category) { category = it } }
            }
            SelectTags(selectedTags = selectedTags) { selectedTags = it }
            ButtonPublish(
                title = title,
                documentInString = documentInString,
                category = category,
                selectedTags = selectedTags
            ) {
                onClickButton()
                title = ""
                documentInString = ""
                category = EnumCategories.NOT_SELECTED.category
                selectedTags = mutableStateListOf()
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }

    @Composable
    private fun ClosingLine() {
        Box(modifier = Modifier.fillMaxWidth().height(3.dp)) {
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .fillMaxHeight()
                    .align(Alignment.Center)
                    .background(color = AdditionalColor, shape = RoundedCornerShape(3.dp))
            )
        }
    }

    @Composable
    private fun AddFileButton(onDocumentInStringChange: (String) -> Unit) {
        val context = LocalContext.current
        var iconDocument by remember { mutableIntStateOf(R.drawable.pdf_add_white) }
        val addedFiles = ActivityResultContracts.GetContent()

        val launcher = rememberLauncherForActivityResult(contract = addedFiles) { uri: Uri? ->
            uri?.let { _ ->
                val inputStream = context.contentResolver.openInputStream(uri)
                inputStream?.use { input ->
                    onDocumentInStringChange(Base64.getEncoder().encodeToString(input.readBytes()))
                    iconDocument = R.drawable.pdf_added_white
                }
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = TextColor, shape = RoundedCornerShape(10.dp))
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    if (iconDocument == R.drawable.pdf_added_white)
                        iconDocument = R.drawable.pdf_add_white
                    else
                        launcher.launch("application/pdf")
                })
            }) {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(iconDocument),
                    contentDescription = "Добавить статью",
                    modifier = Modifier.size(25.dp),
                    tint = TextColor,
                )
                Text(text = "Добавить документ", style = ORDINARY_TEXT)
            }
        }
    }


    @Composable
    private fun SelectCategory(category: String, onCategoryChange: (String) -> Unit) {
        val categories = Categories()
        categories.DropDownContainer(
            onCategoryChange = { onCategoryChange(it) },
            selectedCategory = category
        )
    }

    @Composable
    private fun TitleDocument(title: String, onTitleChange: (String) -> Unit) {
        val standardInput = StandardInput(
            label = "Название документа:",
            placeholder = "Документ №1",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Sentences
            ),
            validColor = if (title.any { it.isDigit() }) Color.Red else TextColor,
            invalidList = listOf(),
            keyboardActions = KeyboardActions(),
            mainBoxModifier = Modifier
                .fillMaxWidth()
                .padding(2.dp, 10.dp, 2.dp, 0.dp),
            textFieldModifier = Modifier
                .fillMaxWidth()
                .height(25.dp)
                .background(color = Color.Transparent)
        )

        standardInput.Input(value = title, onValueChanged = { onTitleChange(it) })
    }

    @Composable
    private fun SelectTags(
        selectedTags: SnapshotStateList<TagPrototype>,
        onSelectedTagsChange: (SnapshotStateList<TagPrototype>) -> Unit
    ) {
        var titleTag by remember { mutableStateOf("") }
        val searchTag = SearchTag()

        searchTag.BasicContainer(
            tags = cacheDocumentTags.value,
            titleTag = titleTag,
            onTitleChange = { titleTag = it },
            selectedTags = selectedTags,
            onSelectedTagsChanged = {
                onSelectedTagsChange(it)
            }
        )
    }

    @Composable
    private fun ButtonPublish(
        title: String,
        documentInString: String,
        category: String,
        selectedTags: SnapshotStateList<TagPrototype>,
        onClickButton: () -> Unit
    ) {
        val context = LocalContext.current
        val coroutineContext = Dispatchers.Main

        val modifierButton = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
        val colorButton = ButtonDefaults.buttonColors(
            backgroundColor = MainColor,
            contentColor = TextColor
        )

        Button(
            modifier = modifierButton,
            colors = colorButton,
            onClick = {
                if (cacheUserProfile.value == null)
                    Toast.makeText(context, "Сначала нужно зарегистрироваться!", Toast.LENGTH_LONG).show()
                else {
                    val fileRequestService = FileRequestServicesImpl()
                    val documentRequestService = DocumentRequestServicesImpl()
                    CoroutineScope(coroutineContext).launch {
                        val file = fileRequestService.addFile(Base64.getDecoder().decode(documentInString))

                        if (file?.id != null) {
                            val document = DocumentPrototype(
                                id = null,
                                title = title,
                                category = category,
                                file_id = file.id,
                                date = LocalDate.now().toString(),
                                image = null,
                                user_id = cacheUserProfile.value?.id!!,
                                tags = selectedTags.map { it.title }.toString(),
                                description = ""
                            )
                            async {
                                Toast.makeText(context, "Документация находится в обработке", Toast.LENGTH_LONG).show()
                                documentRequestService.addDocument(document)
                                Toast.makeText(context, "Документация успешно загружена", Toast.LENGTH_LONG).show()
                            }

                            onClickButton()
                        }
                    }
                }
            }
        ) {
            Text(
                text = "Опубликовать",
                style = HEADING_TEXT,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 7.dp)
            )
        }
    }
}