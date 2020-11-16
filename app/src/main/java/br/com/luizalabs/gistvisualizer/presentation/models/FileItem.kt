package br.com.luizalabs.gistvisualizer.presentation.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FileItem(
        val fileName:String,
        val fileType: String,
        val fileLink: String,
        val fileLang: String?,
): Parcelable