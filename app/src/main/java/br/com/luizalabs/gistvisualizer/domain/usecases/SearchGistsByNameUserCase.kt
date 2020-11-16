package br.com.luizalabs.gistvisualizer.domain.usecases

import br.com.luizalabs.gistvisualizer.domain.data.repository.GistRepository
import br.com.luizalabs.gistvisualizer.domain.entities.Gist

class SearchGistsByNameUserCase(
    private val repository: GistRepository,
    var userName: String? = null
) : UseCase<List<Gist>>(){
    override suspend fun execute(): List<Gist> {
        return repository.getAllGistsByUserName(userName ?: "")
    }
}