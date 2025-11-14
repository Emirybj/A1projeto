package com.example.a1projeto.ui.screens

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.a1projeto.PlaylistApplication
import com.example.a1projeto.data.local.SongCache
import com.example.a1projeto.viewmodel.SongSearchViewModel
import com.example.a1projeto.viewmodel.ViewModelFactory

@Composable
fun SongSearchScreen(navController: NavController, playlistId: Long) {
    val context = LocalContext.current
    val application = context.applicationContext as PlaylistApplication
    val keyboardController = LocalSoftwareKeyboardController.current

    // Pega o ViewModel
    val viewModel: SongSearchViewModel = viewModel(
        factory = ViewModelFactory(application.container.playlistRepository)
    )

    val uiState by viewModel.uiState.collectAsState()

    // Mostra Toast para feedback
    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearMessage() // Limpa a mensagem após exibir
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Buscar Músicas (Playlist ID: $playlistId)",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            //TextField para busca
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = viewModel::onSearchQueryChange,
                label = { Text("Digite o nome da música ou artista") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    viewModel.searchSongs()
                    keyboardController?.hide() // Esconde o teclado
                })
            )

            Spacer(Modifier.height(16.dp))

            //LazyColumn para resultados
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.searchResults) { song ->
                        SongSearchResultItem(
                            song = song,
                            onAddClick = {
                                //Botão Adicionar
                                viewModel.addSongToPlaylist(song, playlistId)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SongSearchResultItem(
    song: SongCache,
    onAddClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagem da Capa
            AsyncImage(
                model = song.urlCapa,
                contentDescription = "Capa do Álbum",
                modifier = Modifier
                    .size(64.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.width(16.dp))

            // Título e Artista
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(song.titulo, style = MaterialTheme.typography.titleMedium, maxLines = 1)
                Text(song.artista, style = MaterialTheme.typography.bodyMedium, maxLines = 1)
            }

            Spacer(Modifier.width(16.dp))

            // Botão Adicionar
            Button(onClick = onAddClick) {
                Text("Add")
            }
        }
    }
}