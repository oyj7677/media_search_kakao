package com.oyj.mediasearch.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.oyj.mediasearch.data.model.Media
import com.oyj.mediasearch.data.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MediaRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _mediaPagingList = MutableStateFlow<PagingData<Media>>(PagingData.empty())
    val mediaPagingList: StateFlow<PagingData<Media>> = _mediaPagingList.asStateFlow()

    fun setQuery(keyword: String) {
        _query.value = keyword
    }

    fun searchMediaPaging(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchImagePaging(query)
                .cachedIn(viewModelScope)
                .collect {
                    _mediaPagingList.value = it
                }
        }
    }
}