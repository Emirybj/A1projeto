package com.example.a1projeto

import android.app.Application
import android.content.Context
import com.example.a1projeto.data.local.AppDatabase
import com.example.a1projeto.data.repository.PlaylistRepository
import com.example.a1projeto.data.repository.PlaylistRepositoryImpl

interface AppContainer { //vai  guardar as instâncias
    val playlistRepository: PlaylistRepository
}

//implementando o container
class AppContainerImpl(private val context: Context) : AppContainer {

    // cria o repositório, que vai criar o DAO e o Database
    override val playlistRepository: PlaylistRepository by lazy {
        PlaylistRepositoryImpl(
            dao = AppDatabase.getInstance(context).playlistDao()
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