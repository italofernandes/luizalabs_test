package br.com.luizalabs.gistvisualizer.data.service.entities

import com.google.gson.annotations.SerializedName

data class GistOwnerResponseModel(
    @SerializedName("id") val id: String,
    @SerializedName("login") val name: String,
    @SerializedName("avatar_url") val avatar: String,
    @SerializedName("url") val gitHubUrl: String,
)