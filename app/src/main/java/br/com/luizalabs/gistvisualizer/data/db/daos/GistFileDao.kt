package br.com.luizalabs.gistvisualizer.data.db.daos

import androidx.room.*
import br.com.luizalabs.gistvisualizer.data.db.entities.GistFileModel

@Dao
interface GistFileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFile(vararg  fileModel: GistFileModel)

    @Delete
    suspend fun deleteAllFiles(vararg  fileModel: GistFileModel)
}