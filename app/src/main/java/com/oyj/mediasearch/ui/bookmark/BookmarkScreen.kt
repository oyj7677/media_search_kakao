package com.oyj.mediasearch.ui.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.oyj.mediasearch.data.model.Media
import com.oyj.mediasearch.ui.search.component.MediaLazyVerticalGrid

@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val mediaPagingList = viewModel.mediaPagingList.collectAsLazyPagingItems()

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchBookmarkList()
    }

    BookmarkScreen(
        mediaPagingList = mediaPagingList,
        onClickCard = { viewModel.deleteMedia(it.mediaUrl, it.thumbnail) },
        modifier = modifier
    )
}

@Composable
fun BookmarkScreen(
    mediaPagingList: LazyPagingItems<Media>,
    onClickCard: (Media) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        MediaLazyVerticalGrid(
            pagingItem = mediaPagingList,
            onClickCard = onClickCard,
            modifier = Modifier
        )
    }
}


