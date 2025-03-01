package com.oyj.mediasearch.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
) {
    companion object {
        private const val SEARCH = "search"
        private const val BOOKMARK = "bookmark"
    }

    data object Search : BottomNavItem(SEARCH, SEARCH, Icons.Filled.Search)
    data object Bookmark : BottomNavItem(BOOKMARK, BOOKMARK, Icons.Filled.Favorite)
}
