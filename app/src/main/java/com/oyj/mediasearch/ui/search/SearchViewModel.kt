package com.oyj.mediasearch.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.oyj.mediasearch.data.model.Media
import com.oyj.mediasearch.data.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MediaRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _mediaPagingList = MutableStateFlow<PagingData<Media>>(PagingData.empty())
    @OptIn(FlowPreview::class)
    val mediaPagingList: StateFlow<PagingData<Media>> =
        _mediaPagingList.asStateFlow()
            .debounce(500L)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = PagingData.empty(),
            )

    fun setQuery(keyword: String) {
        _query.value = keyword
    }

    fun searchMediaPaging() {
        searchMediaPaging(_query.value)
    }

    private fun searchMediaPaging(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchImagePaging(query)
                .cachedIn(viewModelScope)
                .collect {
                    _mediaPagingList.value = it
                }
        }
    }
}