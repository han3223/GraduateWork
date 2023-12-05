package com.example.documentsearch.screens.document

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.documentsearch.R
import com.example.documentsearch.patterns.HeaderFactory
import com.example.documentsearch.prototypes.DocumentWithPercentage
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

class DocumentScreen(documentTags: List<TagPrototype>, documents: List<DocumentWithPercentage>) {
    private val documentTags: List<TagPrototype>
    private val documents: List<DocumentWithPercentage>

    private val heightHeader = 160.dp
    private val headerFactory = HeaderFactory()

    init {
        this.documentTags = documentTags
        this.documents = documents
    }

//    @Composable
//    override fun Content() {
//        Box {
//            Header()
//            Body()
//        }
//    }

    @Composable
    fun Screen() {
        Box {
            Header()
            Body()
        }
    }

    @Composable
    private fun Header() {
        val configuration = LocalConfiguration.current
        val screenWidthDp = configuration.screenWidthDp

        val searchDocument = SearchDocument()
        val searchSupport = SearchSupport(documentTags)

        var isActiveSort by remember { mutableStateOf(false) }
        var isActiveFilter by remember { mutableStateOf(false) }

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
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                searchDocument.SearchEngine()
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
                Spacer(modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth())
            }
        }

        searchSupport.SupportActive(
            isActiveSort = isActiveSort,
            isActiveFilter = isActiveFilter,
            onTapSortChange = { isActiveSort = false },
            onTapFilterChange = { isActiveFilter = false }
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
            items(documents.size) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                ) {
                    DocumentElement(documents[index], rememberLazyScrollState)
                }

                if (index == documents.lastIndex)
                    Spacer(modifier = Modifier.height(90.dp))
            }
        }
    }

    @Composable
    private fun DocumentElement(
        document: DocumentWithPercentage,
        rememberLazyScrollState: LazyListState
    ) {
        val heightWindow = LocalConfiguration.current.screenHeightDp.dp.value
        var isOpenDescription by remember { mutableStateOf(false) }
        val position = remember { mutableStateOf(0.dp) }

        MainInformationDocument(document, position, isOpenDescription) { isOpenDescription = it }
        DescriptionDocument(isOpenDescription, document.document.description)

        LaunchedEffect(key1 = isOpenDescription) {
            if (isOpenDescription) {
                if (position.value.value > heightWindow * 1.75) {
                    val scroll = position.value.value - heightWindow * 1.55
                    rememberLazyScrollState.animateScrollBy(scroll.toFloat())
                }
            }
        }
    }

    @Composable
    private fun MainInformationDocument(
        document: DocumentWithPercentage,
        position: MutableState<Dp>,
        isOpenDescription: Boolean,
        onDescriptionChange: (Boolean) -> Unit
    ) {
        val percent = rememberImagePainter(data = document.percentImage)

        Box(
            modifier = Modifier
                .zIndex(2f)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(size = 33.dp))
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
                        TitleDocument(titleDocument = document.document.title)
                    }
                    PercentageDocument(document.document.percent.toInt().toString(), percent)
                }
                TagsDocument(document.document.tags)

                Row(
                    modifier = Modifier
                        .padding(21.dp, 0.dp, 11.dp, 12.dp)
                        .fillMaxWidth()
                        .background(Color.Transparent, RoundedCornerShape(14.dp)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CategoryAndDateDocument(document.document.category, document.document.date)
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
                    shape = RoundedCornerShape(size = 22.dp)
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
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .horizontalScroll(rememberScrollState())
                    .clip(RoundedCornerShape(14.dp))
                    .padding(horizontal = 5.dp),
                verticalAlignment = Alignment.CenterVertically
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
                    Spacer(modifier = Modifier.width(5.dp))
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
    private fun DescriptionDocument(isOpenDescription: Boolean, description: String) {
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
                    .clip(shape = RoundedCornerShape(size = 33.dp))
                    .background(color = MainColorDark),
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
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    //TODO(Надо сделать скачивание из базы данных)
                                },
                            tint = TextColor
                        )
                    }
                }
            }
        }
    }
}