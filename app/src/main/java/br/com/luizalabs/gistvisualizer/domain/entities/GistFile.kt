package br.com.luizalabs.gistvisualizer.domain.entities

data class GistFile(
    val name: String,
    val type: String,
    val language: String?,
    val url: String
)