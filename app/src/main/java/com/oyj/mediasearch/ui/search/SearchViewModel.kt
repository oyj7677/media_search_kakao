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

    private val _imageList = MutableStateFlow(emptyList<Media>())
    val imageList: StateFlow<List<Media>> = _imageList

    private val _videoList = MutableStateFlow(emptyList<Media>())
    val videoList: StateFlow<List<Media>> = _videoList

    fun setQuery(keyword: String) {
        _query.value = keyword
    }

    fun searchImage() {
        viewModelScope.launch(Dispatchers.IO) {
            _imageList.value = repository.searchImage(query.value)
        }
    }

    fun searchVideo() {
        viewModelScope.launch(Dispatchers.IO) {
            _videoList.value = repository.searchVideo(query.value)
        }
    }
}