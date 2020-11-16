package br.com.luizalabs.gistvisualizer.domain.usecases

import br.com.luizalabs.gistvisualizer.domain.data.repository.GistRepository
import br.com.luizalabs.gistvisualizer.domain.entities.Gist
import br.com.luizalabs.gistvisualizer.domain.exception.NoGistDataException

class GetAllFavoriteGistsUseCase(
    private val repository: GistRepository
): UseCase<List<Gist>>(){

    override suspend fun execute(): List<Gist> {
        val gists = getAllGists()

        if (gists.isEmpty()) {
            throw NoGistDataException()
        }

        return gists
    }

    private suspend fun getAllGists() : List<Gist>{
        return repository.getAllFavGists()
    }
}