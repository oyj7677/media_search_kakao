package com.oyj.mediasearch.ui.search.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun Circle(
    color: Color = Color.Red,
    size: Int = 20,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(size.dp)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            drawCircle(color = color)
        }
    }
}

@Preview
@Composable
private fun CirclePreview() {
    Circle()
}