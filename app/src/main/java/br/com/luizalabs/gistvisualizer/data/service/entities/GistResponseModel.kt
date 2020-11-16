package br.com.luizalabs.gistvisualizer.data.service.entities

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class  GistResponseModel(
    @SerializedName("id") val id: String,
    @SerializedName("created_at") val creationDate: String,
    @SerializedName("owner") val owner: GistOwnerResponseModel,
    @SerializedName("files") val filesJson: JsonObject,
    @SerializedName("description") val description: String?
)
