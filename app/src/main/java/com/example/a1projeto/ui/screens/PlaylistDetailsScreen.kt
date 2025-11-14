package com.example.a1projeto.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.a1projeto.ui.Screen

@Composable
fun PlaylistDetailsScreen(navController: NavController, playlistId: Long) {
    // (Aqui você buscaria os detalhes da playlist usando o playlistId)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // Navega para a tela de busca, passando o ID desta playlist
                navController.navigate(Screen.SongSearch.createRoute(playlistId))
            }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Música")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Tela 2: Detalhes da Playlist")
                Text(text = "ID da Playlist: $playlistId")
                // (Aqui viria a LazyColumn com as músicas salvas)
            }
        }
    }
}