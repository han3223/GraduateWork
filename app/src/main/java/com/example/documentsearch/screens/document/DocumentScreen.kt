package com.example.documentsearch.screens.document

import android.app.Activity.RESULT_OK
import android.os.Environment
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import coil.compose.AsyncImagePainter
import com.example.documentsearch.R
import com.example.documentsearch.api.apiRequests.document.DocumentRequestServicesImpl
import com.example.documentsearch.api.apiRequests.tag.TagRequestServicesImpl
import com.example.documentsearch.cache.CacheDocumentTags
import com.example.documentsearch.cache.CacheDocuments
import com.example.documentsearch.patterns.HeaderFactory
import com.example.documentsearch.patterns.WorkWithPDF
import com.example.documentsearch.prototypes.DocumentPrototype
import com.example.documentsearch.prototypes.TagPrototype
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.AdditionalMainColorDark
import com.example.documentsearch.ui.theme.FILTER
import com.example.documentsearch.ui.theme.HEADING_TEXT
import com.example.documentsearch.ui.theme.HIGHLIGHTING_BOLD_TEXT
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.ORDINARY_TEXT
import com.example.documentsearch.ui.theme.SECONDARY_TEXT
import com.example.documentsearch.ui.theme.SORT
import com.example.documentsearch.ui.theme.TextColor
import java.time.LocalDate


class DocumentScreen() : Screen, Parcelable  {
    private val heightHeader = 160.dp
    private val headerFactory = HeaderFactory()

    private val cacheDocumentTags = CacheDocumentTags()
    private val cacheDocuments = CacheDocuments()

    private val searchDocument = SearchDocument()
    private val searchSupport = SearchSupport(cacheDocumentTags.getDocumentTagsFromCache()?: listOf())

    private val tagRequestService = TagRequestServicesImpl()
    private val documentRequestService = DocumentRequestServicesImpl()

    constructor(parcel: Parcel) : this() {
    }

    @Composable
    override fun Content() {
        var isCompleted by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            getDocumentsAndDocumentTags()
            isCompleted = true
        }

        if (isCompleted) {
            Box {
                Header()
                Body()
            }
        }
    }

    private suspend fun getDocumentsAndDocumentTags() {
        if (cacheDocumentTags.getDocumentTagsFromCache() == null)
            cacheDocumentTags.loadDocumentTags(tagRequestService.getDocumentTags())
        if (cacheDocuments.getDocumentsFromCache() == null)
            cacheDocuments.loadDocuments(documentRequestService.getAllDocuments())
    }

    @Composable
    private fun Header() {
        val configuration = LocalConfiguration.current
        val screenWidthDp = configuration.screenWidthDp

        var isActiveSort by remember { mutableStateOf(false) }
        var isActiveFilter by remember { mutableStateOf(false) }

        var dateFrom by remember { mutableStateOf<LocalDate?>(null) }
        var dateBefore by remember { mutableStateOf<LocalDate?>(null) }
        var category by remember { mutableStateOf<String?>(null) }
        var tags by remember { mutableStateOf<List<TagPrototype>?>(null) }

        if (tags != null && tags!!.isEmpty())
            tags = null

        Box(modifier = Modifier.zIndex(2f)) {
            val headerModifier = if (isActiveSort) Modifier
                .align(Alignment.BottomStart)
                .height(40.dp)
                .width(255.dp)
            else if (isActiveFilter) Modifier
                .align(Alignment.BottomEnd)
                .height(40.dp)
                .width(screenWidthDp.dp - 33.dp)
            else Modifier

            headerFactory.HeaderPrototype(
                height = heightHeader,
                element = if (isActiveSort) SORT else if (isActiveFilter) FILTER else "",
                modifier = headerModifier,
                leftEllipseColor = if (isActiveSort) MainColorDark else MainColor,
                rightEllipseColor = if (isActiveFilter) MainColorDark else MainColor
            )

            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(heightHeader)
                    .padding(20.dp, 0.dp, 20.dp, 40.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                searchDocument.SearchEngine(dateFrom.toString(), dateBefore.toString(), category, tags.toString())
                searchSupport.SupportInActive(
                    onTapSortChange = {
                        if (isActiveFilter) isActiveFilter = false
                        else isActiveSort = !isActiveSort
                    },
                    onTapFilterChange = {
                        if (isActiveSort) isActiveSort = false
                        else isActiveFilter = !isActiveFilter
                    }
                )
            }
        }

        searchSupport.SupportActive(
            isActiveSort = isActiveSort,
            isActiveFilter = isActiveFilter,
            onTapSortChange = { isActiveSort = false },
            onTapFilterChange = { isActiveFilter = false },
            onDateFromChange = { dateFrom = it },
            onDateBeforeChange = { dateBefore = it },
            onCategoryChange = { category = it },
            onSelectedTagsChange = { tags = it }
        )
    }

    @Composable
    private fun Body() {
        val rememberLazyScrollState = rememberLazyListState()

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, heightHeader - 33.dp, 5.dp, 0.dp),
            state = rememberLazyScrollState
        ) {
            cacheDocuments.getDocumentsFromCache()?.let { documents ->
                itemsIndexed(items = documents) {index, document ->
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)) {
                        DocumentElement(document, rememberLazyScrollState)
                    }

                    if (index == documents.lastIndex)
                        Spacer(modifier = Modifier.height(90.dp))
                }
            }
        }
    }

    @Composable
    private fun DocumentElement(document: DocumentPrototype, scrollState: LazyListState) {
        val context = LocalContext.current
        val workWithPDF = WorkWithPDF()
        val heightWindow = LocalConfiguration.current.screenHeightDp.dp.value
        var isOpenDescription by remember { mutableStateOf(false) }
        val position = remember { mutableStateOf(0.dp) }

        val openFileResultLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // Handle the result of the file opening activity
            if (result.resultCode == RESULT_OK) {
                // File opened successfully
            }
        }

        MainInformationDocument(document, position, isOpenDescription) { isOpenDescription = it }
        DescriptionDocument(isOpenDescription, document.description?: "") {
            val fileName = "${document.title}.pdf"
            try {
                val outputUri = workWithPDF.createNewPdfFileUri(context, fileName)
                val outputStream = context.contentResolver.openOutputStream(outputUri)
                outputStream?.use { output ->
                    output.write(document.document)
                }
                Toast.makeText(context, "Файл успешно загружен в ${Environment.DIRECTORY_DOCUMENTS}", Toast.LENGTH_LONG).show()

                openFileResultLauncher.launch(workWithPDF.openFile(context, "${Environment.DIRECTORY_DOCUMENTS}/$fileName"))
            } catch (exception: Exception) {
                Toast.makeText(context, "Возникла ошибка при загрузке файла", Toast.LENGTH_LONG).show()
                Log.i("Ошибка", "Ошибка в загрузке файла: ${exception.message}")
            }
        }

        LaunchedEffect(key1 = isOpenDescription) {
            if (isOpenDescription) {
                if (position.value.value > heightWindow * 1.75) {
                    val scroll = position.value.value - heightWindow * 1.55
                    scrollState.animateScrollBy(scroll.toFloat())
                }
            }
        }
    }

    @Composable
    private fun MainInformationDocument(
        document: DocumentPrototype,
        position: MutableState<Dp>,
        isOpenDescription: Boolean,
        onDescriptionChange: (Boolean) -> Unit
    ) {
//        val percent = rememberAsyncImagePainter(model = percentageCompliance[document.percent.toInt()])

        Box(
            modifier = Modifier
                .zIndex(2f)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(size = 28.dp))
                .background(color = MainColorLight)
                .onGloballyPositioned { coordinates ->
                    position.value = coordinates.positionInWindow().y.dp
                }
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .padding(start = 11.dp, top = 11.dp, end = 11.dp)
                        .fillMaxWidth(),
                ) {
                    ImageDocument()
                    Box(modifier = Modifier.weight(1f)) {
                        TitleDocument(titleDocument = document.title)
                    }
                    //PercentageDocument(document.percent.toInt().toString(), percent)
                }
                TagsDocument(document.tags)

                Row(
                    modifier = Modifier
                        .padding(21.dp, 0.dp, 11.dp, 12.dp)
                        .fillMaxWidth()
                        .background(Color.Transparent, RoundedCornerShape(14.dp)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CategoryAndDateDocument(document.category, document.date)
                    DescriptionArrow(isOpenDescription = isOpenDescription) { onDescriptionChange(it) }
                }
            }
        }
    }

    @Composable
    private fun ImageDocument() {
        Box(
            modifier = Modifier
                .padding(end = 10.dp)
                .size(84.dp)
                .background(
                    color = AdditionalColor,
                    shape = RoundedCornerShape(size = 17.dp)
                )
        ) {
            // TODO(Сюда надо будет положить картинку документа из базы данных)
        }
    }

    @Composable
    private fun TitleDocument(titleDocument: String) {
        Box {
            Text(
                text = titleDocument,
                style = HEADING_TEXT
            )
        }
    }

    @Composable
    private fun PercentageDocument(percent: String, percentImage: AsyncImagePainter) {
        Box(
            modifier = Modifier
                .padding(start = 10.dp)
                .size(30.dp)
                .clip(CircleShape)
                .zIndex(1f)
        ) {
            Image(
                painter = percentImage,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
            )
            Text(
                text = percent,
                modifier = Modifier
                    .align(Alignment.Center)
                    .zIndex(2f),
                style = ORDINARY_TEXT.merge(TextStyle(color = Color.Black))
            )
        }
    }

    @Composable
    private fun TagsDocument(tags: List<String>) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(11.dp, 10.dp, 11.dp, 5.dp)
                .clip(RoundedCornerShape(18.dp))
                .height(35.dp)
                .background(AdditionalMainColorDark),
        ) {
            BackgroundTags()
            Tags(tags)
        }
    }

    @Composable
    private fun BackgroundTags() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
                .clip(RoundedCornerShape(17.dp))
                .shadow(
                    40.dp,
                    RoundedCornerShape(14.dp),
                    ambientColor = Color.White,
                    spotColor = Color.White
                )
        )
    }

    @Composable
    private fun Tags(tags: List<String>) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(rememberScrollState())
                .clip(RoundedCornerShape(14.dp))
                .padding(horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            tags.forEach { title ->
                Box(
                    modifier = Modifier
                        .border(1.dp, Color(0xCC354643), RoundedCornerShape(14.dp))
                        .background(
                            color = MainColorDark,
                            shape = RoundedCornerShape(size = 14.dp)
                        )
                ) {
                    Text(
                        text = title,
                        style = ORDINARY_TEXT,
                        modifier = Modifier.padding(
                            vertical = 5.dp,
                            horizontal = 10.dp
                        )
                    )
                }
            }
        }
    }

    @Composable
    private fun CategoryAndDateDocument(category: String, date: String) {
        Row(
            modifier = Modifier.padding(horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .background(color = MainColorDark, shape = CircleShape)
            )
            Text(
                text = category,
                style = SECONDARY_TEXT
            )
            Text(
                text = "  |  ",
                style = SECONDARY_TEXT,
            )
            Text(
                text = date,
                style = SECONDARY_TEXT,
            )
        }
    }

    @Composable
    private fun DescriptionArrow(isOpenDescription: Boolean, onDescriptionChange: (Boolean) -> Unit) {
        val animatedRotate by animateFloatAsState(if (isOpenDescription) 180f else 0f, label = "")
        Box(
            modifier = Modifier
                .padding(start = 10.dp)
                .size(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = "Open",
                modifier = Modifier
                    .size(90.dp)
                    .padding(start = 5.dp)
                    .clickable {
                        onDescriptionChange(!isOpenDescription)
                    }
                    .align(Alignment.Center)
                    .rotate(animatedRotate),
                tint = MainColorDark,
            )
        }
    }

    @Composable
    private fun DescriptionDocument(isOpenDescription: Boolean, description: String, onClickDownloadChange: () -> Unit) {
        AnimatedVisibility(
            visible = isOpenDescription,
            enter = slideInVertically() + expandVertically(expandFrom = Alignment.Top) + fadeIn(),
            exit = slideOutVertically() + shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(),
            modifier = Modifier
                .zIndex(1f)
                .padding(top = 90.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(size = 28.dp))
                    .background(color = AdditionalMainColorDark),
            ) {
                Column(modifier = Modifier.padding(21.dp, 100.dp, 15.dp, 0.dp)) {
                    Text(
                        text = "Описание",
                        modifier = Modifier.padding(bottom = 5.dp),
                        style = HIGHLIGHTING_BOLD_TEXT
                    )
                    Text(
                        text = description,
                        style = ORDINARY_TEXT,
                        modifier = Modifier.padding(bottom = 3.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.download),
                            contentDescription = "Скачать",
                            tint = TextColor,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    onClickDownloadChange()
                                }
                        )
                    }
                }
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DocumentScreen> {
        override fun createFromParcel(parcel: Parcel): DocumentScreen {
            return DocumentScreen(parcel)
        }

        override fun newArray(size: Int): Array<DocumentScreen?> {
            return arrayOfNulls(size)
        }
    }
}