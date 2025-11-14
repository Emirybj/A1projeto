package com.example.a1projeto.data.repository

import com.example.a1projeto.data.local.Playlist
import com.example.a1projeto.data.local.PlaylistWithSongs
import com.example.a1projeto.data.local.SongCache
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {

    // --- Funções da Tela 1 (Emily) ---
    fun getAllPlaylists(): Flow<List<Playlist>>
    fun searchPlaylistByName(query: String): Flow<List<Playlist>>
    suspend fun insertPlaylist(playlist: Playlist)
    suspend fun deletePlaylist(playlistId: Long)

    // --- Funções da Tela 3 (Caio) ---
    suspend fun searchApiForSong(query: String): List<SongCache>
    suspend fun addSongToPlaylist(song: SongCache, playlistId: Long)

    // --- Funções da Tela 2 (Murilo) ---
    fun getPlaylistWithSongs(playlistId: Long): Flow<PlaylistWithSongs?>
    suspend fun deleteSongFromPlaylist(playlistId: Long, songApiId: String)
}