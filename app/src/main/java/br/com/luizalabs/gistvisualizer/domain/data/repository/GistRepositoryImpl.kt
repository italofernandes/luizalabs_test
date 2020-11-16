package br.com.luizalabs.gistvisualizer.domain.data.repository

import br.com.luizalabs.gistvisualizer.domain.data.datasource.FavoriteGistDataSource
import br.com.luizalabs.gistvisualizer.domain.data.datasource.GistServiceDataSource
import br.com.luizalabs.gistvisualizer.domain.entities.Gist
import br.com.luizalabs.gistvisualizer.domain.exception.GistServiceCommunicationException
import java.lang.Exception

class GistRepositoryImpl(
    private val gistServiceDataSource: GistServiceDataSource,
    private val dataSourceFavorite: FavoriteGistDataSource
): GistRepository {
    override suspend fun getAllGistsByPage(page: Int): List<Gist> {
        try {
            return gistServiceDataSource.getGists(page)
        } catch (e: Exception) {
            throw GistServiceCommunicationException(e.message)
        }
    }

    override suspend fun getAllGistsByUserName(name: String): List<Gist> {
        try {
            return gistServiceDataSource.findGistsByUserName(name)
        } catch (e: Exception) {
            throw GistServiceCommunicationException(e.message)
        }
    }

    override suspend fun getAllFavGists(): List<Gist> {
        return dataSourceFavorite.getAllSavedGists()
    }

    override suspend fun saveGist(gist: Gist) {
        dataSourceFavorite.saveGist(gist)
    }

    override suspend fun deleteGist(gist: Gist) {
        dataSourceFavorite.deleteGist(gist)
    }

    override suspend fun gistIsFavorite(gistId: String): Boolean {
        return dataSourceFavorite.checkGistFavoriteById(gistId)
    }
}