package br.com.luizalabs.gistvisualizer.presentation.converters

import br.com.luizalabs.gistvisualizer.commons.BaseConverter
import br.com.luizalabs.gistvisualizer.domain.entities.Gist
import br.com.luizalabs.gistvisualizer.domain.entities.GistFile
import br.com.luizalabs.gistvisualizer.presentation.models.FileItem
import br.com.luizalabs.gistvisualizer.presentation.models.GistItem

class GistItemToGistConverter : BaseConverter<GistItem, Gist> {
    override fun convert(from: GistItem): Gist {
        return Gist(
            id = from.id,
            creationDate = from.creationDate,
            ownerName = from.ownerName,
            ownerAvatar = from.avatarUri,
            ownerGitHubUrl = from.gitHubUrl,
            files = convertIntoFileList(from.files),
            description = from.description
        )
    }

    private fun convertIntoFileList(
        fileItemList: List<FileItem>
    ): List<GistFile> {
        return fileItemList.map { fileItem ->
            GistFile(
                name = fileItem.fileName,
                type = fileItem.fileType,
                url = fileItem.fileLink,
                language = fileItem.fileLang
            )
        }
    }
}
