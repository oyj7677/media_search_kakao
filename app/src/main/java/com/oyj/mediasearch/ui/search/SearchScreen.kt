package com.oyj.mediasearch.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.oyj.mediasearch.data.model.Media
import com.oyj.mediasearch.data.model.MediaImage
import com.oyj.mediasearch.ui.search.component.MediaLazyVerticalGrid
import com.oyj.mediasearch.ui.search.component.SearchBar
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val pagingItem = viewModel.mediaPagingList.collectAsLazyPagingItems()
    val query by viewModel.query.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = query) {
        if (query.isEmpty()) return@LaunchedEffect
        viewModel.searchMediaPaging()
    }

    SearchScreen(
        query = query,
        pagingItem = pagingItem,
        onValueChange = {
            viewModel.setQuery(it)
        },
        onClickCard = {
            viewModel.saveMedia(it)
        },
        modifier = modifier
    )
}

@Composable
fun SearchScreen(
    query: String,
    pagingItem: LazyPagingItems<Media>,
    onValueChange: (String) -> Unit = {},
    onClickCard: (Media) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        SearchBar(
            query = query,
            onValueChange = onValueChange,
            modifier = Modifier
        )

        MediaLazyVerticalGrid(
            pagingItem = pagingItem,
            onClickCard = onClickCard,
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    // 가짜 데이터 생성
    val fakeData: List<Media> = List(10) { index ->
        MediaImage(
            thumbnail = "https://www.google.com/images/branding/googlelogo$index/2x/googlelogo_color_92x30dp.png",
            dateTime = "2021-10-10",
            mediaUrl = "https://www.google.com/images/branding/googlelogo$index/2x/googlelogo_color_92x30dp.png",
            sources = "google.com",
            imgUrl = "https://www.google.com/images/branding/googlelogo$index/2x/googlelogo_color_92x30dp.png",
            isBookmark = false
        )
    }
    val fakePagingData = PagingData.from(fakeData)
    val fakeFlow = MutableStateFlow(fakePagingData)

    // LazyPagingItems로 변환
    val lazyPagingItems = fakeFlow.collectAsLazyPagingItems()

    var query by remember { mutableStateOf("") }
    SearchScreen(
        query = query,
        pagingItem = lazyPagingItems,
        onValueChange = { query = it }
    )
}