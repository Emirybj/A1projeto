package com.example.a1projeto.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song_cache")
data class SongCache(
    @PrimaryKey // O ID da API (ex: "deezer-12345") é a chave
    val songApiId: String,
    val titulo: String,
    val artista: String,
    val urlCapa: String,
    val urlAudio: String // O 'preview' do Deezer
)
