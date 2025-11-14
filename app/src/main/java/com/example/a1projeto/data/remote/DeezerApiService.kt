package com.example.a1projeto.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface DeezerApiService {

    // Define a função de busca
    // Ex: https://api.deezer.com/search?q=eminem
    @GET("search")
    suspend fun searchSong(
        @Query("q") query: String
    ): SongResponse // Retorna o DTO principal

    companion object {
        const val BASE_URL = "https://api.deezer.com/"
    }
}