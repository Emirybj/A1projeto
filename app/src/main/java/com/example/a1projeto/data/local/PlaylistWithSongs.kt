package com.example.a1projeto.data.local

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

// Esta classe NÃO é uma tabela (@Entity)
// É apenas uma DTO (Data Transfer Object) para os resultados da consulta
data class PlaylistWithSongs(
    // Pega a "Playlist" principal
    @Embedded
    val playlist: Playlist,

    // E relaciona com as "Músicas" (SongCache)
    @Relation(
        parentColumn = "playlistId", // Chave da Playlist
        entityColumn = "songApiId", // Chave da SongCache
        associateBy = Junction(
            value = PlaylistSongCrossRef::class, // A tabela de junção
            parentColumn = "playlistId", // Na junção, qual é a chave da Playlist
            entityColumn = "songApiId"  // Na junção, qual é a chave da Música
        )
    )
    val songs: List<SongCache> // A lista de músicas
)