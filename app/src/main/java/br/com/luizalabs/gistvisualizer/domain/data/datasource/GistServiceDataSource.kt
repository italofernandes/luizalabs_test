package br.com.luizalabs.gistvisualizer.domain.data.datasource

import br.com.luizalabs.gistvisualizer.domain.entities.Gist

interface GistServiceDataSource {
    suspend fun getGists(page: Int): List<Gist>
    suspend fun findGistsByUserName(name: String): List<Gist>
}