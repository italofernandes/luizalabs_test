package br.com.luizalabs.gistvisualizer.domain.usecases

import br.com.luizalabs.gistvisualizer.domain.data.repository.GistRepository
import br.com.luizalabs.gistvisualizer.domain.entities.Gist

class SearchGistsByNameUserCase(
    private val repository: GistRepository,
) : UseCaseWithParams<List<Gist>>(){

    private var userName: String? = null

    override suspend fun execute(): List<Gist> {
        return repository.getAllGistsByUserName(userName ?: "")
    }

    override fun configures(vararg args: Any) {
        userName = args[0] as String
    }
}