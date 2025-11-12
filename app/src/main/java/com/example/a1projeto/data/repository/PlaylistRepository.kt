package com.example.a1projeto.data.repository

import com.example.a1projeto.data.local.Playlist
import com.example.a1projeto.data.local.SongCache
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {

    fun getAllPlaylists(): Flow<List<Playlist>>

    fun searchPlaylistByName(query: String): Flow<List<Playlist>>

    suspend fun insertPlaylist(playlist: Playlist)

    suspend fun deletePlaylist(playlistId: Long)

    // TODO: Adicionar as outras funções do DAO (insertSong, etc.) quando forem necessárias
}