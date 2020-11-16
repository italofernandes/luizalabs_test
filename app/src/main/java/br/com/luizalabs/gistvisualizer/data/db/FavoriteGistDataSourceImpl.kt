package br.com.luizalabs.gistvisualizer.data.db

import br.com.luizalabs.gistvisualizer.data.db.converters.GistModelToGistConverter
import br.com.luizalabs.gistvisualizer.data.db.converters.GistToGistModelConverter
import br.com.luizalabs.gistvisualizer.data.db.daos.GistDao
import br.com.luizalabs.gistvisualizer.data.db.daos.GistFileDao
import br.com.luizalabs.gistvisualizer.domain.data.datasource.FavoriteGistDataSource
import br.com.luizalabs.gistvisualizer.domain.entities.Gist

class FavoriteGistDataSourceImpl(
    private val dao: GistDao,
    private val fileDao: GistFileDao,
    private val modelConverter: GistToGistModelConverter,
    private val gistConverter: GistModelToGistConverter
) : FavoriteGistDataSource {

    override suspend fun getAllSavedGists(): List<Gist> {
        return dao.getAllGists().map {
            modelConverter.convert(it)
        }
    }

    override suspend fun deleteGist(gist: Gist) {

        val gistModel = dao.getGistById(gist.id)

        val gist = gistModel.info

        fileDao.deleteAllFiles()
        dao.deleteGist(gist)
    }

    override suspend fun saveGist(gist: Gist) {

        val gistModel = gistConverter.convert(gist)

        val gist = gistModel.info
        val files = gistModel.files

        dao.insertOrUpdateGist(gist)
        fileDao.saveFile(*files.toTypedArray())
    }

    override suspend fun checkGistFavoriteById(gistId: String): Boolean {
        return dao.exists(gistId)
    }
}