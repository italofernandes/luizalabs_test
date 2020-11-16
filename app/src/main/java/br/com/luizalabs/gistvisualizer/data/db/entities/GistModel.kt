package br.com.luizalabs.gistvisualizer.data.db.entities

import androidx.room.*

@Entity
data class GistModel(
    @PrimaryKey val id: String,
    @ColumnInfo(name ="creation") val creationDate: String,
    @ColumnInfo(name ="name") val ownerName: String,
    @ColumnInfo(name ="avatar") val ownerAvatar: String,
    @ColumnInfo(name ="gitHubUrl") val ownerGitHubUrl: String,
    @ColumnInfo(name ="favorite") val favorite: Boolean,
    @ColumnInfo(name ="desc") val description: String?,
)