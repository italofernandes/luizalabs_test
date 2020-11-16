package br.com.luizalabs.gistvisualizer.data.service.converters

import br.com.luizalabs.gistvisualizer.commons.BaseConverter
import br.com.luizalabs.gistvisualizer.data.service.entities.GistFileResponseModel
import br.com.luizalabs.gistvisualizer.data.service.entities.GistOwnerResponseModel
import br.com.luizalabs.gistvisualizer.data.service.entities.GistResponseModel
import br.com.luizalabs.gistvisualizer.domain.entities.Gist
import br.com.luizalabs.gistvisualizer.domain.entities.GistFile
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject

class ResponseToGistConverter(
    private val gson: Gson
): BaseConverter<List<GistResponseModel>, List<Gist>> {

    override fun convert(from: List<GistResponseModel>): List<Gist> {
        return from.map {
            Gist(
                id = it.id,
                creationDate = it.creationDate,
                ownerName = it.owner.name,
                ownerAvatar = it.owner.avatar,
                ownerGitHubUrl = it.owner.gitHubUrl,
                files = transformGistFile(it.filesJson),
                description = it.description
            )
        }
    }

    private fun transformGistFile(
        jsonObject: JsonObject
    ):List<GistFile>{
        val fileArray = mutableListOf<GistFileResponseModel>()
        val entrySet = jsonObject.entrySet()

        for (entry: Map.Entry<String, JsonElement> in entrySet){
            val jsonNode = entry.value.toString()
            val gistFileModel = gson.fromJson(jsonNode, GistFileResponseModel::class.java)

            fileArray.add(gistFileModel)
        }

        return fileArray.map {
            GistFile(
                name = it.name,
                type =  it.type,
                language = it.language,
                url = it.url
            )
        }
    }
}