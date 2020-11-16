package br.com.luizalabs.gistvisualizer.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.luizalabs.gistvisualizer.data.db.daos.GistDao
import br.com.luizalabs.gistvisualizer.data.db.daos.GistFileDao
import br.com.luizalabs.gistvisualizer.data.db.entities.GistFileModel
import br.com.luizalabs.gistvisualizer.data.db.entities.GistModel

@Database(entities = [GistModel::class, GistFileModel::class], version = 2)
abstract class AppDataBase: RoomDatabase() {
    abstract fun getGistDao(): GistDao
    abstract fun getGistFileDao(): GistFileDao
}