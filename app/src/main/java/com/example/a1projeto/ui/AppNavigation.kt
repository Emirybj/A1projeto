package com.example.a1projeto.ui


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a1projeto.ui.screens.PlaylistScreen
import com.example.a1projeto.ui.screens.PlaylistDetailsScreen
import com.example.a1projeto.ui.screens.SongSearchScreen


sealed class Screen(val route: String) {
    object PlaylistList : Screen("playlist_list_screen") // Tela 1
    object PlaylistDetails : Screen("playlist_details_screen") // Tela 2
    object SongSearch : Screen("song_search_screen") // Tela 3
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

        // Detalhes da Playlist
        composable(route = Screen.PlaylistDetails.route) {
            PlaylistDetailsScreen(navController = navController)
        }

        // Busca de Músicas
        composable(route = Screen.SongSearch.route) {
            SongSearchScreen(navController = navController)
        }
    }
}