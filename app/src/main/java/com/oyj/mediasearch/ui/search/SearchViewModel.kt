package com.oyj.mediasearch.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oyj.mediasearch.data.model.Media
import com.oyj.mediasearch.data.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
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
        viewModelScope.launch(Dispatchers.IO) {
            _mediaList.value =  repository.searchMedia(query.value)
        }
    }
}