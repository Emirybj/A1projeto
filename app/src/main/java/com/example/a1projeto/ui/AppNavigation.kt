package com.example.a1projeto.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.a1projeto.ui.screens.PlaylistDetailsScreen
import com.example.a1projeto.ui.screens.PlaylistListScreen
import com.example.a1projeto.ui.screens.SongSearchScreen


sealed class Screen(val route: String) {
    object PlaylistList : Screen("playlist_list_screen")

    // (Detalhes)
    object PlaylistDetails : Screen("playlist_details_screen/{playlistId}") {
        fun withArgs(playlistId: Long): String {
            return "playlist_details_screen/$playlistId"
        }
    }

    // (Busca)
    object SongSearch : Screen("song_search_screen/{playlistId}") {
        fun withArgs(playlistId: Long): String {
            return "song_search_screen/$playlistId"
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.PlaylistList.route
    ) {
        // Lista de Playlists
        composable(route = Screen.PlaylistList.route) {
            PlaylistListScreen(navController = navController)
        }

        // Detalhes da Playlist (Recebe o ID)
        composable(
            route = Screen.PlaylistDetails.route,
            arguments = listOf(navArgument("playlistId") { type = NavType.LongType })
        ) { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getLong("playlistId") ?: 0L
            PlaylistDetailsScreen(
                navController = navController,
                playlistId = playlistId
            )
        }

        //  Busca de Músicas (AGORA RECEBE O ID)
        composable(
            route = Screen.SongSearch.route,
            arguments = listOf(navArgument("playlistId") { type = NavType.LongType })
        ) { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getLong("playlistId") ?: 0L
            SongSearchScreen(
                navController = navController,
                playlistId = playlistId // <-- Passa o ID para a Tela 3
            )
        }
    }
}