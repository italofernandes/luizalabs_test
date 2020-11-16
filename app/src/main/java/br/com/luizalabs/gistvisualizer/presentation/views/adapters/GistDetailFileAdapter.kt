package br.com.luizalabs.gistvisualizer.presentation.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.luizalabs.gistvisualizer.R
import br.com.luizalabs.gistvisualizer.presentation.models.FileItem
import kotlinx.android.synthetic.main.file_item_layout.view.*

class GistDetailFileAdapter(
    private val files: List<FileItem>,
    private val onClick: ((String) -> Unit)?
): RecyclerView.Adapter<FileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.file_item_layout, parent, false)
        return FileViewHolder(rootView, onClick)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.bind(files[position])
    }

    override fun getItemCount(): Int = files.size
}

class FileViewHolder(
     private val view: View,
     private val onClick: ((String) -> Unit)?
): RecyclerView.ViewHolder(view) {
    fun bind(fileItem: FileItem) {
        view.fileItemName.setText(fileItem.fileName)
        view.setOnClickListener {
            onClick?.invoke(fileItem.fileLink)
        }
    }
}