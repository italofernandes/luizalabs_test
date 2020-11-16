package br.com.luizalabs.gistvisualizer.presentation.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.luizalabs.gistvisualizer.R
import br.com.luizalabs.gistvisualizer.presentation.extentions.loadUri
import br.com.luizalabs.gistvisualizer.presentation.models.GistItem
import kotlinx.android.synthetic.main.gist_item.view.*

class GistItemAdapter(
    var gistItemList: List<GistItem>? = null,
): RecyclerView.Adapter<GistViewHolder>() {

    var favoriteCallBack: ((Int) -> Unit)? = null
    var clickCallBack: ((GistItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GistViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.gist_item, parent, false)
        return GistViewHolder(rootView, favoriteCallBack, clickCallBack)
    }

    override fun onBindViewHolder(holder: GistViewHolder, position: Int) {
        gistItemList?.let { gistList ->
            holder.bind(gistList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return gistItemList?.size ?: 0
    }
}

class GistViewHolder(
    private val view: View,
    private val favoriteCallBack: ((Int) -> Unit)?,
    private val clickCallBack: ((GistItem) -> Unit)?
): RecyclerView.ViewHolder(view){

    private fun clear(){
        view.gistUserName.setText("")
        view.gistType.setText("")
        view.gistCreationDate.setText("")
        view.gistUserAvatar.loadUri("")
        view.gistFavButton.setColorFilter(view.context.resources.getColor(R.color.unfav_color))
    }

    fun bind(gist: GistItem, position: Int){

        clear()

        view.gistUserName.setText(gist.ownerName)
        view.gistType.setText(gist.type)
        view.gistCreationDate.setText(gist.creationDate)
        view.gistUserAvatar.loadUri(gist.avatarUri)

        view.gistFavButton.setOnClickListener{
            favoriteCallBack?.invoke(position)
        }

        view.setOnClickListener{
            clickCallBack?.invoke(gist)
        }

        //TODO REMOVE DEPRECATED
        when{
            gist.favorite -> view.gistFavButton.setColorFilter(view.context.resources.getColor(R.color.fav_color))
            else -> view.gistFavButton.setColorFilter(view.context.resources.getColor(R.color.unfav_color))
        }
    }
}
