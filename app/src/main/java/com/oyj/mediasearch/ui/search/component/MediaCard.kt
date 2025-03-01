package com.oyj.mediasearch.ui.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.oyj.mediasearch.R

@Composable
fun MediaCard(
    imgUrl: String,
    date: String,
    onClickCard: () -> Unit = {},
    mediaMark: @Composable BoxScope.() -> Unit = {},
    bookMark: @Composable BoxScope.() -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable { onClickCard() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),

            ) {
            AsyncImage(
                model = imgUrl,
                contentDescription = stringResource(id = R.string.text_media_card_content_description),
                contentScale = ContentScale.Fit,
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart)
                    .aspectRatio(1f)
                    .background(Color.Gray)

            )
            mediaMark()
            bookMark()
        }
        Text(
            text = date,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MediaCardPreview() {
    val imgUrl = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png"
    MediaCard(
        imgUrl = imgUrl,
        date = "2021-10-10",
        mediaMark = {
            Text(
                text = "이미지 or 비디오 컴포넌트입니다.",
                modifier = Modifier.align(Alignment.TopStart)
            )
        },
        bookMark = {
            Text(
                text = "북마크 컴포넌트입니다.",
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }
    )
}