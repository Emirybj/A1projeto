package com.example.a1projeto.data.local

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "playlist_song_cross_ref",
    primaryKeys = ["playlistId", "songApiId"], // Chave primária composta
    foreignKeys = [
        // Chave estrangeira para a tabela de Playlists
        ForeignKey(
            entity = Playlist::class,
            parentColumns = ["playlistId"],
            childColumns = ["playlistId"],
            onDelete = ForeignKey.CASCADE // Se apagar a playlist, apaga a referência
        ),
    // Chave estrangeira para a tabela de Músicas (SongCache)
        ForeignKey(
            entity = SongCache::class,
            parentColumns = ["songApiId"],
            childColumns = ["songApiId"],
            onDelete = ForeignKey.CASCADE // Se apagar a música do cache, apaga a referência

        )
    ]
)


data class PlaylistSongCrossRef(
    val playlistId: Long,
    val songApiId: String,
    val ordem: Int
)
