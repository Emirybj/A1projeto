package com.example.a1projeto.ui

// --- VERIFIQUE SE TODAS ESTAS IMPORTAÇÕES ESTÃO AQUI ---
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a1projeto.ui.screens.PlaylistScreen
import com.example.a1projeto.ui.screens.PlaylistDetailsScreen
import com.example.a1projeto.ui.screens.SongSearchScreen

// --------------------------------------------------

// Um objeto 'sealed' é ótimo para definir rotas de forma segura
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
        // Rota para a Tela 1: Lista de Playlists
        composable(route = Screen.PlaylistList.route) {
            PlaylistScreen(navController = navController)
        }

        // Rota para a Tela 2: Detalhes da Playlist
        composable(route = Screen.PlaylistDetails.route) {
            PlaylistDetailsScreen(navController = navController)
        }

        // Rota para a Tela 3: Busca de Músicas
        composable(route = Screen.SongSearch.route) {
            SongSearchScreen(navController = navController)
        }
    }
}