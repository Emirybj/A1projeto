package com.example.a1projeto.data.remote

import com.google.gson.annotations.SerializedName

// DTOs (Data Transfer Objects) que batem com o JSON da API do Deezer

// Objeto principal da resposta
data class SongResponse(
    @SerializedName("data")
    val data: List<SongData>
    // Pode adicionar "total", "next", etc., se precisar de paginação
)

// Objeto de cada música
data class SongData(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("artist")
    val artist: ArtistData,
    @SerializedName("album")
    val album: AlbumData,
    @SerializedName("preview")
    val previewUrl: String // URL do áudio de 30s
)

// Objeto do artista
data class ArtistData(
    @SerializedName("name")
    val name: String
)

// Objeto do álbum
data class AlbumData(
    @SerializedName("cover_medium")
    val coverUrl: String // URL da capa do álbum
)