package br.com.luizalabs.gistvisualizer.presentation.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GistItem (
    val id: String,
    val ownerName: String,
    val gitHubUrl: String,
    val type: String,
    val creationDate: String,
    var favorite: Boolean,
    val avatarUri: String,
    val files: List<FileItem>,
    val description: String?
): Parcelable