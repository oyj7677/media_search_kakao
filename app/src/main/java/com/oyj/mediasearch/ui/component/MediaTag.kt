package com.oyj.mediasearch.ui.component

import android.R
import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MediaTag(
    name: String,
    @ColorRes color: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = name,
        textAlign = TextAlign.Center,
        modifier = modifier
            .background(
                color = colorResource(
                    id = color
                )
            )
            .padding(horizontal = 4.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun MediaTagPreview() {
    MediaTag(
        name = "ImageMark",
        color = R.color.holo_blue_light,
        modifier = Modifier.wrapContentSize()
    )
}