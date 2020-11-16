package br.com.luizalabs.gistvisualizer.data.db.entities

import androidx.room.Embedded
import androidx.room.Relation

data class GistWithOwnerAndFile(
    @Embedded
    var info: GistModel,
    @Relation(parentColumn = "id", entityColumn = "gistId", entity = GistFileModel::class)
    var files: List<GistFileModel>
)