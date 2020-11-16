package br.com.luizalabs.gistvisualizer.data.db.converters

import br.com.luizalabs.gistvisualizer.commons.BaseConverter
import br.com.luizalabs.gistvisualizer.domain.entities.Gist
import br.com.luizalabs.gistvisualizer.domain.entities.GistFile
import br.com.luizalabs.gistvisualizer.data.db.entities.GistFileModel
import br.com.luizalabs.gistvisualizer.data.db.entities.GistModel
import br.com.luizalabs.gistvisualizer.data.db.entities.GistWithOwnerAndFile

class GistModelToGistConverter: BaseConverter<Gist, GistWithOwnerAndFile> {
    override fun convert(from: Gist): GistWithOwnerAndFile {
        return GistWithOwnerAndFile(
            info = GistModel(
                    id = from.id,
                    creationDate = from.creationDate,
                    favorite =  from.favorite,
                    ownerName = from.ownerName,
                    ownerAvatar = from.ownerAvatar,
                    ownerGitHubUrl = from.ownerGitHubUrl,
                    description = from.description
            ),
            files = convertFiles(from.files, from.id)
        )
    }

    private fun convertFiles(
        from: List<GistFile>,
        gistId: String
    ): List<GistFileModel>{
        return from.map {file ->
            GistFileModel(
                name = file.name,
                type = file.type,
                language = file.language,
                url = file.url,
                gistId = gistId
            )
        }
    }
}