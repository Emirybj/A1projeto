package com.example.a1projeto.data.repository

import com.example.a1projeto.data.local.Playlist
import com.example.a1projeto.data.local.SongCache
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {

    fun getAllPlaylists(): Flow<List<Playlist>>

    fun searchPlaylistByName(query: String): Flow<List<Playlist>>

    suspend fun insertPlaylist(playlist: Playlist)

    suspend fun deletePlaylist(playlistId: Long)

    /** Busca músicas na API externa (Deezer) */
    suspend fun searchApiForSong(query: String): List<SongCache>

    /** Adiciona uma música (do cache) a uma playlist específica */
    suspend fun addSongToPlaylist(song: SongCache, playlistId: Long)


}