package com.example.a1projeto.data.local.dao

import android.icu.text.StringSearch
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.a1projeto.data.local.Playlist
import com.example.a1projeto.data.local.PlaylistSongCrossRef
import com.example.a1projeto.data.local.SongCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Dao
interface PlaylistDao {

    // Cria uma nova playlist
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: Playlist)

    // Busca todas as playlists (para tela 1)
    @Query("SELECT * FROM playlists ORDER BY nome ASC")
    fun getAllPlaylists(): Flow<List<Playlist>>

    // buscar playlist por nome
    @Query("SELECT * FROM playlists WHERE nome LIKE '%' || :searchQuery || '%'")
    fun searchPlaylistByName(searchQuery: String): Flow<List<Playlist>>

    //Apaga uma Playlist
    @Query("DELETE FROM playlists WHERE playlistId = :playlistId")
    suspend fun deletePlaylist(playlistId: Long)

    //Renomeia uma playlist
    @Query(value = "UPDATE playlists SET nome = :newName WHERE playlistId = :playlistId")
    suspend fun updatePlaylistName(playlistId: Long, newName: String)

    // adiciona uma música na tabela Cache
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSong(song: SongCache)

    //adiciona a relação entre playlist e música
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistSongCrossRef(crossRef: PlaylistSongCrossRef)

    //remove a relação entre playlist e musica
    @Query("DELETE FROM playlist_song_cross_ref WHERE playlistId = :playlistId AND songApiId = :songApiId")
    suspend fun deleteSongFromPlaylist(playlistId: Long, songApiId: String)

}