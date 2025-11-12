package com.example.a1projeto.data.repository

import com.example.a1projeto.data.local.Playlist
import com.example.a1projeto.data.local.dao.PlaylistDao
import kotlinx.coroutines.flow.Flow

class PlaylistRepositoryImpl(
    private val dao: PlaylistDao
) : PlaylistRepository { // E implementa a interface

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return dao.getAllPlaylists()
    }

    override fun searchPlaylistByName(query: String): Flow<List<Playlist>> {
        return dao.searchPlaylistByName(query)
    }

    override suspend fun insertPlaylist(playlist: Playlist) {
        dao.insertPlaylist(playlist)
    }

    override suspend fun deletePlaylist(playlistId: Long) {
        dao.deletePlaylist(playlistId)
    }

}
