package com.oyj.mediasearch.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.oyj.mediasearch.domain.model.Media
import com.oyj.mediasearch.domain.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val repository: MediaRepository
) : ViewModel() {

    private val _mediaPagingList = MutableStateFlow<PagingData<Media>>(PagingData.empty())

    val mediaPagingList: StateFlow<PagingData<Media>> = _mediaPagingList

    fun deleteMedia(mediaUrl: String, thumbnail: String) {
        viewModelScope.launch {
            repository.deleteBookmark(mediaUrl, thumbnail)
        }
    }

    fun fetchBookmarkList() {
        viewModelScope.launch {
            repository.getBookmarkList()
                .cachedIn(viewModelScope)
                .collect {
                    _mediaPagingList.value = it
                }
        }
    }
}