package br.com.luizalabs.gistvisualizer.domain.data.datasource

import br.com.luizalabs.gistvisualizer.domain.entities.Gist

interface FavoriteGistDataSource {
    suspend fun getAllSavedGists(): List<Gist>
    suspend fun deleteGist(gist: Gist)
    suspend fun saveGist(gist: Gist)
    suspend fun checkGistFavoriteById(gistId: String): Boolean
}