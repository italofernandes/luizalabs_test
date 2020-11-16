package br.com.luizalabs.gistvisualizer.domain.entities

data class Gist(
    val id: String,
    val creationDate: String,
    val ownerName: String,
    val ownerAvatar: String,
    val ownerGitHubUrl: String,
    val files: List<GistFile>,
    val description: String?,
    var favorite: Boolean = false,
)