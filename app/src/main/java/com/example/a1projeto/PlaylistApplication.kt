package com.example.a1projeto

import android.app.Application
import android.content.Context
import com.example.a1projeto.data.local.AppDatabase
import com.example.a1projeto.data.remote.DeezerApiService
import com.example.a1projeto.data.repository.PlaylistRepository
import com.example.a1projeto.data.repository.PlaylistRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer { //vai guardar as instâncias
    val playlistRepository: PlaylistRepository
    // Adiciona o serviço de API ao container
    val deezerApiService: DeezerApiService
}

//implementando o container
class AppContainerImpl(private val context: Context) : AppContainer {

    //Cria a instância do Retrofit/DeezerApiService
    override val deezerApiService: DeezerApiService by lazy {
        Retrofit.Builder()
            .baseUrl(DeezerApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeezerApiService::class.java)
    }

    //Atualiza o repositório para receber o apiService
    override val playlistRepository: PlaylistRepository by lazy {
        PlaylistRepositoryImpl(
            dao = AppDatabase.getInstance(context).playlistDao(),
            apiService = deezerApiService // Injeta o serviço de API
        )
    }

}

class PlaylistApplication : Application() {
    // instância do container
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        // cria o container quando o app inicia
        container = AppContainerImpl(this)
    }
}