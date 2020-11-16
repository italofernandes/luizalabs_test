package br.com.luizalabs.gistvisualizer.domain.usecases

import br.com.luizalabs.gistvisualizer.domain.data.repository.GistRepository
import br.com.luizalabs.gistvisualizer.domain.entities.Gist
import br.com.luizalabs.gistvisualizer.domain.exception.GistServiceCommunicationException
import br.com.luizalabs.gistvisualizer.domain.exception.NoGistDataException

class GetAllGistsUseCase(
    private val repository: GistRepository
): UseCaseWithParams<List<Gist>>(){

    private var page: Int = 0

    override suspend fun execute(): List<Gist> {
        try {
            val gists = repository.getAllGistsByPage(page)

            if (gists.isEmpty()) {
                throw NoGistDataException()
            }

            gists.forEach {
                it.favorite = repository.gistIsFavorite(gistId = it.id)
            }

            return gists
        } catch (e: GistServiceCommunicationException) {
            throw e
        }
    }

    override fun configures(vararg args: Any) {
        page = args[0] as Int
    }
}