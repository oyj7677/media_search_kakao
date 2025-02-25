package com.oyj.mediasearch.ui.search

import androidx.lifecycle.ViewModel
import com.oyj.mediasearch.data.model.Media
import com.oyj.mediasearch.data.repository.MediaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel(
    private val repository: MediaRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _mediaList = MutableStateFlow(emptyList<Media>())
    val mediaList: StateFlow<List<Media>> = _mediaList

    fun setQuery(keyword: String) {
        _query.value = keyword
    }

    fun searchMedia() {
        _mediaList.value = repository.searchMedia(query.value)
    }
}