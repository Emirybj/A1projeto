package com.example.a1projeto.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1projeto.data.local.SongCache
import com.example.a1projeto.data.repository.PlaylistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SongSearchViewModel(
    private val repository: PlaylistRepository
) : ViewModel() {

    // Define o UiState para a tela de busca
    data class SongSearchUiState(
        val searchQuery: String = "",
        val searchResults: List<SongCache> = emptyList(),
        val isLoading: Boolean = false,
        val message: String? = null // Para feedback (ex: "Música adicionada!")
    )

    private val _uiState = MutableStateFlow(SongSearchUiState())
    val uiState = _uiState.asStateFlow()

    // Função chamada quando o texto de busca muda
    fun onSearchQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    // Função para executar a busca
    fun searchSongs() {
        if (_uiState.value.searchQuery.isBlank()) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, message = null)
            val results = repository.searchApiForSong(_uiState.value.searchQuery)
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                searchResults = results
            )
        }
    }

    // Função para adicionar a música à playlist
    fun addSongToPlaylist(song: SongCache, playlistId: Long) {
        viewModelScope.launch {
            repository.addSongToPlaylist(song, playlistId)
            _uiState.value = _uiState.value.copy(
                message = "${song.titulo} adicionada com sucesso!"
            )
        }
    }

    // Limpa a mensagem de feedback
    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }
}