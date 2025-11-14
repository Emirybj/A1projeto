package com.example.a1projeto.ui.screens

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistListScreen(navController: NavController) {

    // Conexão com ViewModel e estados
    val context = LocalContext.current
    val application = context.applicationContext as PlaylistApplication
    val viewModel: PlaylistViewModel = viewModel(
        factory = ViewModelFactory(application.container.playlistRepository)
    )
    val uiState by viewModel.uiState.collectAsState()
    var newPlaylistName by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Minhas Playlists") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Campos de Criar e Buscar
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = newPlaylistName,
                    onValueChange = { newPlaylistName = it },
                    label = { Text("Nova Playlist") },
                    modifier = Modifier.weight(1f)
                )
                Button(onClick = {
                    viewModel.insertPlaylist(newPlaylistName)
                    newPlaylistName = ""
                }) { Text("Criar") }
            }

            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { query -> viewModel.onSearchQueryChange(query) },
                label = { Text("Buscar playlists por nome...") },
                modifier = Modifier.fillMaxWidth()
            )

            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.allPlaylists) { playlist ->
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        navController.navigate(
                                            Screen.PlaylistDetails.withArgs(playlist.playlistId)
                                        )
                                    }
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = playlist.nome,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(onClick = {
                                    viewModel.deletePlaylist(playlist)
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Excluir Playlist"
                                    )
                                }
                            }
                        }
                    }
                }

                if (uiState.allPlaylists.isEmpty() && uiState.searchQuery.isNotEmpty()) {
                    Text(
                        text = "Nenhuma playlist encontrada com o nome \"${uiState.searchQuery}\"",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

            }
        }
    }
}