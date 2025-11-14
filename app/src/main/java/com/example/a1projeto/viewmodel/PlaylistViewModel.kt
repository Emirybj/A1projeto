package com.example.a1projeto.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1projeto.data.local.Playlist
import com.example.a1projeto.data.repository.PlaylistRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val repository: PlaylistRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlaylistUiState())
    val uiState = _uiState.asStateFlow()

    // Variável para guardar o "trabalho" de coleta da lista
    private var getPlaylistsJob: Job? = null

    init {
        // Inicia a busca com uma string vazia (que retorna todas)
        onSearchQueryChange("")
    }

    // Função para a busca
    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }

        // Cancela a coleta anterior (se houver) para começar uma nova
        getPlaylistsJob?.cancel()

        // Inicia uma nova coleta (seja a busca ou a lista completa)
        getPlaylistsJob = viewModelScope.launch {
            val flow = if (query.isBlank()) {
                repository.getAllPlaylists() // Se a busca for vazia, pega todas
            } else {
                repository.searchPlaylistByName(query) // Se não, busca pelo nome
            }

            // Coleta o resultado (filtrado ou não) e atualiza o estado
            flow.collect { playlists ->
                _uiState.update { it.copy(allPlaylists = playlists) }
            }
        }
    }

    // Função para o CRUD - Create
    fun insertPlaylist(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            repository.insertPlaylist(
                Playlist(nome = name)
            )
        }
    }

    // --- CÓDIGO NOVO (Excluir Playlist) ---
    fun deletePlaylist(playlist: Playlist) {
        viewModelScope.launch {
            repository.deletePlaylist(playlist.playlistId)
        }
    }
    // --- FIM DO CÓDIGO NOVO ---
}

// Data class do estado (continua igual)
data class PlaylistUiState(
    val allPlaylists: List<Playlist> = emptyList(),
    val searchQuery: String = ""
)