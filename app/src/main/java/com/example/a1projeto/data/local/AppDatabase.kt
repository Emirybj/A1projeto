package com.example.a1projeto.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a1projeto.data.local.dao.PlaylistDao

@Database(
    entities = [
        Playlist::class,
        SongCache::class,
        PlaylistSongCrossRef::class
    ], // lista de todas as tabelas (entidades)
    version = 1, // mude para 2, 3, etc se alterar a estrutura
    exportSchema = false // desativar exportação de schema
)
abstract class AppDatabase : RoomDatabase() {
    //função  abstrata que vai retornar o DAO o room implementa isso
    abstract fun playlistDao(): PlaylistDao

    // Padrão 'Singleton': garante que só exista UMA instância do banco de dados no apptodo.
    companion object {
        @Volatile //vai garantir que a instância seja visivel para todas as threads
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            // 'synchronized' garante que apenas uma thread por vez crie a instância, caso ela não exista
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "playlist_database.db" //arquivo do banco
                    )
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}