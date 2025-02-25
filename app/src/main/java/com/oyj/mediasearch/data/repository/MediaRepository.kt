package com.oyj.mediasearch.data.repository

import com.oyj.mediasearch.data.model.Media

interface MediaRepository {
    fun searchImage(keyword: String): List<Media>
}