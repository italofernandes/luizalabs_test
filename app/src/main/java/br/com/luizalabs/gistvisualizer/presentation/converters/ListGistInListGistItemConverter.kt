package br.com.luizalabs.gistvisualizer.presentation.converters

import br.com.luizalabs.gistvisualizer.commons.BaseConverter
import br.com.luizalabs.gistvisualizer.domain.entities.Gist
import br.com.luizalabs.gistvisualizer.domain.entities.GistFile
import br.com.luizalabs.gistvisualizer.presentation.models.FileItem
import br.com.luizalabs.gistvisualizer.presentation.models.GistItem
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ListGistInListGistItemConverter: BaseConverter<List<Gist>, List<GistItem>> {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt-br"))
    private val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale("pt-br"))

    override fun convert(from: List<Gist>): List<GistItem> {
        return from.map { gist ->
            GistItem(
                id = gist.id,
                ownerName = gist.ownerName,
                favorite = gist.favorite,
                avatarUri = gist.ownerAvatar,
                gitHubUrl = gist.ownerGitHubUrl,
                type = formatType(gist.files),
                creationDate = gist.creationDate,
                files = transformListFiles(gist.files),
                description = gist.description
            )
        }
    }

    private fun transformListFiles(
            listFiles: List<GistFile>
    ):List<FileItem>{
        return listFiles.map {file ->
            FileItem(
                fileName = file.name,
                fileType = file.type,
                fileLink = file.url,
                fileLang = file.language
            )
        }
    }

    private fun formatType(
        files: List<GistFile>
    ): String {
        return if(files.size > 1){
            "Multiplos Tipos"
        } else if (files.size == 1) {
            files[0].type
        } else {
            "Não contem Aqruivo"
        }
    }
}