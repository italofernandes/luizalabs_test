package br.com.luizalabs.gistvisualizer.domain.data.repository

import br.com.luizalabs.gistvisualizer.domain.entities.Gist

interface GistRepository {
    suspend fun getAllGistsByPage(page: Int): List<Gist>
    suspend fun getAllGistsByUserName(name: String): List<Gist>
    suspend fun getAllFavGists(): List<Gist>
    suspend fun saveGist(gist: Gist)
    suspend fun deleteGist(gist: Gist)
    suspend fun gistIsFavorite(gistId: String): Boolean
}