package br.com.luizalabs.gistvisualizer.data.db.converters

import br.com.luizalabs.gistvisualizer.commons.BaseConverter
import br.com.luizalabs.gistvisualizer.data.db.entities.GistFileModel
import br.com.luizalabs.gistvisualizer.data.db.entities.GistWithOwnerAndFile
import br.com.luizalabs.gistvisualizer.domain.entities.Gist
import br.com.luizalabs.gistvisualizer.domain.entities.GistFile

class GistToGistModelConverter: BaseConverter<GistWithOwnerAndFile, Gist> {
    override fun convert(from: GistWithOwnerAndFile): Gist {
        return Gist(
                id =  from.info.id,
                creationDate = from.info.creationDate,
                files = convertFiles(from.files),
                favorite = from.info.favorite,
                ownerName = from.info.ownerName,
                ownerAvatar = from.info.ownerAvatar,
                ownerGitHubUrl = from.info.ownerGitHubUrl,
                description = from.info.description
            )
    }

    private fun convertFiles(
        from: List<GistFileModel>
    ): List<GistFile>{
        return from.map {file ->
            GistFile(
                name = file.name,
                type = file.type,
                language = file.language,
                url = file.url
            )
        }
    }
}