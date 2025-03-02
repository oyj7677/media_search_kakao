package com.oyj.mediasearch.ui.component

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.oyj.mediasearch.domain.model.Media
import com.oyj.mediasearch.domain.model.MediaImage
import com.oyj.mediasearch.domain.model.MediaVideo
import com.oyj.mediasearch.ui.search.component.Circle
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun MediaLazyVerticalGrid(
    pagingItem: LazyPagingItems<Media>,
    onClickCard: (Media) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(
            count = pagingItem.itemCount,
            key = { index -> pagingItem[index]?.mediaUrl ?: index }
        ) { index ->
            val media = pagingItem[index]
            if (media != null) {
                MediaCard(
                    imgUrl = media.thumbnail,
                    date = media.dateTime,
                    onClickCard = { onClickCard(media) },
                    mediaMark = {
                        when (media) {
                            is MediaImage -> {
                                MediaTag(
                                    name = "이미지",
                                    color = R.color.holo_blue_light,
                                    modifier = Modifier.align(Alignment.TopStart).padding(4.dp)
                                )
                            }

                            is MediaVideo -> {
                                MediaTag(
                                    name = "비디오",
                                    color = R.color.holo_red_light,
                                    modifier = Modifier.align(Alignment.TopStart).padding(4.dp)
                                )
                            }
                        }
                    },
                    bookMark = {
                        if (media.isBookmark) {
                           Circle(
                               modifier = Modifier.align(Alignment.TopEnd).padding(4.dp)
                           )
                        }
                    },
                    modifier = Modifier
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MediaLazyVerticalGridPreview() {
    // 가짜 데이터 생성
    val data: List<Media> = List(30) { index ->
        MediaImage(
            thumbnail = "https://www.google.com/images/branding/googlelogo$index/2x/googlelogo_color_92x30dp.png",
            dateTime = "2021-10-10",
            mediaUrl = "https://www.google.com/images/branding/googlelogo$index/2x/googlelogo_color_92x30dp.png",
            sources = "google.com",
            imgUrl = "https://www.google.com/images/branding/googlelogo$index/2x/googlelogo_color_92x30dp.png",
            isBookmark = false
        )
    }
    val pagingData = PagingData.from(data)
    val pagingDataStateFlow = MutableStateFlow(pagingData)

    // LazyPagingItems로 변환
    val lazyPagingItems = pagingDataStateFlow.collectAsLazyPagingItems()
    MediaLazyVerticalGrid(
        pagingItem = lazyPagingItems
    )
}