package com.example.a1projeto.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.a1projeto.ui.screens.PlaylistScreen
import com.example.a1projeto.ui.screens.PlaylistDetailsScreen
import com.example.a1projeto.ui.screens.SongSearchScreen

sealed class Screen(val route: String) {
    object PlaylistList : Screen("playlist_list_screen")
    object PlaylistDetails : Screen("playlist_details_screen/{playlistId}") {
        fun createRoute(playlistId: Long) = "playlist_details_screen/$playlistId"
    }
    object SongSearch : Screen("song_search_screen/{playlistId}") {
        fun createRoute(playlistId: Long) = "song_search_screen/$playlistId"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController() // Controla a navegação

    NavHost(
        navController = navController,
        startDestination = Screen.PlaylistList.route // Define a tela inicial
    ) {
        //  Lista de Playlists
        composable(route = Screen.PlaylistList.route) {
            PlaylistScreen(navController = navController)
        }

        // Detalhes da Playlist (com argumento)
        composable(
            route = Screen.PlaylistDetails.route,
            arguments = listOf(navArgument("playlistId") { type = NavType.LongType })
        ) { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getLong("playlistId") ?: 0L
            PlaylistDetailsScreen(navController = navController, playlistId = playlistId)
        }

        // Busca de Músicas (com argumento)
        composable(
            route = Screen.SongSearch.route,
            arguments = listOf(navArgument("playlistId") { type = NavType.LongType })
        ) { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getLong("playlistId") ?: 0L
            SongSearchScreen(navController = navController, playlistId = playlistId)
        }
    }
}