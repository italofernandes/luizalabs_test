package br.com.luizalabs.gistvisualizer.data.db.daos

import androidx.room.*
import br.com.luizalabs.gistvisualizer.data.db.entities.GistModel
import br.com.luizalabs.gistvisualizer.data.db.entities.GistWithOwnerAndFile

@Dao
interface GistDao {
    @Transaction
    @Query("SELECT * FROM gistmodel")
    suspend fun getAllGists(): List<GistWithOwnerAndFile>

    @Delete
    suspend fun deleteGist(gist: GistModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateGist(gist: GistModel)

    @Query("SELECT EXISTS (SELECT 1 FROM gistmodel WHERE id = :id)")
    fun exists(id: String): Boolean

    @Transaction
    @Query("SELECT * FROM gistmodel WHERE id = :id ")
    fun getGistById(id: String): GistWithOwnerAndFile
}