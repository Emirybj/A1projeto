package com.example.a1projeto.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.a1projeto.data.local.Playlist
import com.example.a1projeto.data.local.PlaylistSongCrossRef
import com.example.a1projeto.data.local.PlaylistWithSongs // Importar
import com.example.a1projeto.data.local.SongCache
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: Playlist)

    @Query("SELECT * FROM playlists ORDER BY nome ASC")
    fun getAllPlaylists(): Flow<List<Playlist>>

    @Query("SELECT * FROM playlists WHERE nome LIKE '%' || :searchQuery || '%'")
    fun searchPlaylistByName(searchQuery: String): Flow<List<Playlist>>

    @Query("DELETE FROM playlists WHERE playlistId = :playlistId")
    suspend fun deletePlaylist(playlistId: Long)

    @Query("UPDATE playlists SET nome = :newName WHERE playlistId = :playlistId")
    suspend fun updatePlaylistName(playlistId: Long, newName: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSong(song: SongCache)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistSongCrossRef(crossRef: PlaylistSongCrossRef)

    @Query("SELECT COUNT(songApiId) FROM playlist_song_cross_ref WHERE playlistId = :playlistId")
    suspend fun getSongCountInPlaylist(playlistId: Long): Int

    @Query("DELETE FROM playlist_song_cross_ref WHERE playlistId = :playlistId AND songApiId = :songApiId")
    suspend fun deleteSongFromPlaylist(playlistId: Long, songApiId: String)

    @Transaction
    @Query("SELECT * FROM playlists WHERE playlistId = :playlistId")
    fun getPlaylistWithSongs(playlistId: Long): Flow<PlaylistWithSongs?>
}