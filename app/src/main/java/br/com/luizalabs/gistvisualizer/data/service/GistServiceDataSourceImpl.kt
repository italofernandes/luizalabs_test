package br.com.luizalabs.gistvisualizer.data.service

import br.com.luizalabs.gistvisualizer.data.service.converters.ResponseToGistConverter
import br.com.luizalabs.gistvisualizer.domain.data.datasource.GistServiceDataSource
import br.com.luizalabs.gistvisualizer.domain.entities.Gist

class GistServiceDataSourceImpl(
    private val service: GistService,
    private val converterList: ResponseToGistConverter
): GistServiceDataSource{
    override suspend fun getGists(page: Int): List<Gist> {
        val result = service.getGistsByPage(page)
        return converterList.convert(result)
    }

    override suspend fun findGistsByUserName(name: String): List<Gist> {
        val result = service.searchGistsByUser(name)
        return converterList.convert(result)
    }
}