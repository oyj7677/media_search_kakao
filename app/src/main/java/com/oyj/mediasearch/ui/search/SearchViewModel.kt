package com.oyj.mediasearch.ui.search

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oyj.mediasearch.data.model.Media
import com.oyj.mediasearch.data.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
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
            val response = repository.searchMedia(query.value)
            _mediaList.value =  sortDateListDescending(response)
        }
    }

    private fun sortDateListDescending(dates: List<Media>): List<Media> {
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

        return dates.sortedByDescending { media ->
            OffsetDateTime.parse(media.dateTime, formatter)
        }
    }
}