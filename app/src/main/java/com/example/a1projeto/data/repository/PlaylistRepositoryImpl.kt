package com.example.a1projeto.data.repository

import com.example.a1projeto.data.local.Playlist
import com.example.a1projeto.data.local.PlaylistSongCrossRef
import com.example.a1projeto.data.local.PlaylistWithSongs
import com.example.a1projeto.data.local.SongCache
import com.example.a1projeto.data.local.dao.PlaylistDao
import com.example.a1projeto.data.remote.DeezerApiService
import kotlinx.coroutines.flow.Flow

// Atualiza o construtor para receber o serviço da API
class PlaylistRepositoryImpl(
    private val dao: PlaylistDao,
    private val apiService: DeezerApiService // Injeção do serviço
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




    override suspend fun searchApiForSong(query: String): List<SongCache> {
        return try {
            // Chama a API
            val response = apiService.searchSong(query)
            // Mapeia o DTO (SongData) para a entidade local (SongCache)
            response.data.map { songData ->
                SongCache(
                    songApiId = "deezer-${songData.id}", // Adiciona um prefixo para evitar conflitos
                    titulo = songData.title,
                    artista = songData.artist.name,
                    urlCapa = songData.album.coverUrl,
                    urlAudio = songData.previewUrl
                )
            }
        } catch (e: Exception) {
            // Em caso de erro (ex: sem internet), retorna lista vazia
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun addSongToPlaylist(song: SongCache, playlistId: Long) {
        // 1. Garante que a música exista na tabela de cache local
        dao.insertSong(song)

        // 2. Descobre a próxima "ordem"
        val ordem = dao.getSongCountInPlaylist(playlistId)

        // 3. Cria a relação
        val crossRef = PlaylistSongCrossRef(
            playlistId = playlistId,
            songApiId = song.songApiId,
            ordem = ordem + 1 // Adiciona como a última música
        )
        dao.insertPlaylistSongCrossRef(crossRef)
    }

    // --- Implementações da Tela 2 (Murilo) ---
    override fun getPlaylistWithSongs(playlistId: Long): Flow<PlaylistWithSongs?> {
        return dao.getPlaylistWithSongs(playlistId)
    }

    override suspend fun deleteSongFromPlaylist(playlistId: Long, songApiId: String) {
        dao.deleteSongFromPlaylist(playlistId, songApiId)
    }


}