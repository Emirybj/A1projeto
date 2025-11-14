package com.example.a1projeto.ui.screens

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.a1projeto.PlaylistApplication
import com.example.a1projeto.data.local.Playlist
import com.example.a1projeto.ui.Screen
import com.example.a1projeto.viewmodel.PlaylistViewModel
import com.example.a1projeto.viewmodel.ViewModelFactory

@Composable
fun PlaylistScreen(navController: NavController) {
    // Pega o ViewModel usando a Factory do AppContainer
    val application = LocalContext.current.applicationContext as PlaylistApplication
    val viewModel: PlaylistViewModel = viewModel(
        factory = ViewModelFactory(application.container.playlistRepository)
    )

    // Coleta o UiState
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = "Minhas Playlists",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            items(uiState.allPlaylists) { playlist ->
                PlaylistItem(
                    playlist = playlist,
                    onClick = {
                        // Navega para detalhes passando o ID
                        navController.navigate(Screen.PlaylistDetails.createRoute(playlist.playlistId))
                    }
                )
            }
        }
    }
}

@Composable
fun PlaylistItem(playlist: Playlist, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.MusicNote,
                contentDescription = "Playlist Icon",
                modifier = Modifier.padding(end = 16.dp)
            )
            Column {
                Text(text = playlist.nome, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}