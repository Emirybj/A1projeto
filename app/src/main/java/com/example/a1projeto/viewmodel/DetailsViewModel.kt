package com.example.a1projeto.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1projeto.data.local.PlaylistWithSongs
import com.example.a1projeto.data.repository.PlaylistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// Esta data class representa o estado da Tela 2 (Detalhes)
data class DetailsUiState(
    val playlistWithSongs: PlaylistWithSongs? = null,
    val isLoading: Boolean = true
)

class DetailsViewModel(
    private val repository: PlaylistRepository
) : ViewModel() {

    // _uiState é o estado interno, mutável
    private val _uiState = MutableStateFlow(DetailsUiState())
    // uiState é o estado público, imutável, que a tela vai "ouvir"
    val uiState = _uiState.asStateFlow()

    private var currentPlaylistId: Long? = null

    // Função que a Tela 2 vai chamar para carregar uma playlist
    fun loadPlaylist(playlistId: Long) {
        // Evita recarregar a mesma playlist
        if (playlistId == currentPlaylistId) return

        currentPlaylistId = playlistId
        _uiState.value = DetailsUiState(isLoading = true) // Mostra o "Loading"

        viewModelScope.launch {
            repository.getPlaylistWithSongs(playlistId).collect { playlistData ->
                _uiState.value = DetailsUiState(
                    playlistWithSongs = playlistData,
                    isLoading = false // Esconde o "Loading"
                )
            }
        }
    }

    // Função que a Tela 2 vai chamar para deletar uma música (RF03)
    fun deleteSong(songApiId: String) {
        val playlistId = currentPlaylistId ?: return // Precisa saber de qual playlist deletar

        viewModelScope.launch {
            repository.deleteSongFromPlaylist(
                playlistId = playlistId,
                songApiId = songApiId
            )
            // O Flow vai atualizar a lista automaticamente!
        }
    }
}