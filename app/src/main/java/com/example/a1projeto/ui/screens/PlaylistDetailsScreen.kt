package com.example.a1projeto.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
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
    // (Conexão com ViewModel e estados)
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
                title = { Text(uiState.playlistWithSongs?.playlist?.nome ?: "Carregando...") },
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
                Icon(imageVector = Icons.Default.MusicNote, contentDescription = "Adicionar música")
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

                            AsyncImage(
                                model = song.urlCapa,
                                contentDescription = "Capa do Álbum",
                                modifier = Modifier
                                    .size(50.dp) // Tamanho pequeno e fixo
                                    .clip(MaterialTheme.shapes.small), // Cantos arredondados
                                contentScale = ContentScale.Crop
                            )

                            Spacer(Modifier.width(12.dp))


                            Column(modifier = Modifier.weight(1f)) {
                                Text(song.titulo, style = MaterialTheme.typography.titleMedium)
                                Text(song.artista, style = MaterialTheme.typography.bodySmall)
                            }


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