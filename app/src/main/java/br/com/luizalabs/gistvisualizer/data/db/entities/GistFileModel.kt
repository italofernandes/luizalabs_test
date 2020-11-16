package br.com.luizalabs.gistvisualizer.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GistFileModel(
    @PrimaryKey val name: String,
    @ColumnInfo(name ="type") val type: String,
    @ColumnInfo(name ="language") val language: String?,
    @ColumnInfo(name ="url") val url: String,
    @ColumnInfo(name ="gistId") val gistId: String
)