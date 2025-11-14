package com.example.a1projeto.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MusicNote // <-- IMPORTAR
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.a1projeto.PlaylistApplication
import com.example.a1projeto.ui.Screen
import com.example.a1projeto.viewmodel.DetailsViewModel
import com.example.a1projeto.viewmodel.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistDetailsScreen(
    navController: NavController,
    playlistId: Long
) {
    // (Conexão com ViewModel)
    val context = LocalContext.current
    val application = context.applicationContext as PlaylistApplication
    val viewModel: DetailsViewModel = viewModel(
        factory = ViewModelFactory(application.container.playlistRepository)
    )
    val uiState by viewModel.uiState.collectAsState()

    // (Carregar os dados)
    LaunchedEffect(playlistId) {
        viewModel.loadPlaylist(playlistId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(uiState.playlistWithSongs?.playlist?.nome ?: "Carregando...")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.SongSearch.withArgs(playlistId))
            }) {
                // Trocamos o ícone de "+" por "Nota Musical"
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = "Adicionar música"
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else if (uiState.playlistWithSongs != null) {
                // (LazyColumn com o botão de deletar)
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.playlistWithSongs!!.songs) { song ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = song.titulo,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = {
                                viewModel.deleteSong(song.songApiId)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Excluir Música"
                                )
                            }
                        }
                    }
                }
            } else {
                Text("Playlist não encontrada.")
            }
        }
    }
}