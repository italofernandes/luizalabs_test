package br.com.luizalabs.gistvisualizer.data.service.entities

import com.google.gson.annotations.SerializedName

data class GistFileResponseModel(
    @SerializedName("filename") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("language") val language: String,
    @SerializedName("raw_url") val url: String
)