package com.example.a1projeto.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1projeto.data.local.Playlist
import com.example.a1projeto.data.repository.PlaylistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
class PlaylistViewModel(
    private val repository: PlaylistRepository
) : ViewModel() {

    // _uiState é o estado interno, mutável, que o ViewModel controla
    private val _uiState = MutableStateFlow(PlaylistUiState())
    // uiState é o estado público, imutavel, que a tela (UI) vai "ouvir"
    val uiState = _uiState.asStateFlow()

    // o init é executado assim que o viewmodel e criado
    init {
        // inicia a coleta de todas as playlists do repositório
        viewModelScope.launch {
            repository.getAllPlaylists().collect { playlists ->
                _uiState.value = _uiState.value.copy(
                    allPlaylists = playlists
                )
            }
        }
    }

    data class PlaylistUiState(
        val allPlaylists: List<Playlist> = emptyList(),
        val searchQuery: String = ""
    )
}