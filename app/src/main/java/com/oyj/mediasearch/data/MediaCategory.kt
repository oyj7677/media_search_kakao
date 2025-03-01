package com.oyj.mediasearch.data

sealed class MediaCategory {
    data object Image : MediaCategory()
    data object Video : MediaCategory()
}