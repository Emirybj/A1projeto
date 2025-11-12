package com.example.a1projeto.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists")
data class Playlist(
    @PrimaryKey(autoGenerate = true)
    val playlistId: Long = 0, // Chave primária que vai se auto-incrementar
    val nome: String
)
