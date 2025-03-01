package com.oyj.mediasearch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.oyj.mediasearch.ui.bookmark.BookmarkScreen
import com.oyj.mediasearch.ui.search.SearchScreen

@Composable
fun NavigationGraph(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = BottomNavItem.Search.route
    ) {
        composable(BottomNavItem.Search.route) {
            SearchScreen()
        }
        composable(BottomNavItem.Bookmark.route) {
            BookmarkScreen()
        }
    }
}