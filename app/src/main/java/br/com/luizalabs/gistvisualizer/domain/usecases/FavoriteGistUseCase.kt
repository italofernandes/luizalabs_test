package br.com.luizalabs.gistvisualizer.domain.usecases

import br.com.luizalabs.gistvisualizer.domain.data.repository.GistRepository
import br.com.luizalabs.gistvisualizer.domain.entities.Gist

class FavoriteGistUseCase(
    private val repository: GistRepository
): UseCaseWithParams<Unit>() {

    private var gist: Gist? = null

    override suspend fun execute() {
        gist?.let {
            val isFavorite = repository.gistIsFavorite(it.id)
            when{
                isFavorite -> {
                    repository.deleteGist(it)
                }
                else -> {
                    it.favorite = true
                    repository.saveGist(it)
                }
            }
        }
    }

    override fun configures(vararg args: Any) {
        gist = args[0] as Gist
    }
}