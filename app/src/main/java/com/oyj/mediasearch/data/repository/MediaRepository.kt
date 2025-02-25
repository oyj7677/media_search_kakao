package com.oyj.mediasearch.data.repository

import com.oyj.mediasearch.data.model.Media

interface MediaRepository {
    fun searchMedia(keyword: String): List<Media>
}