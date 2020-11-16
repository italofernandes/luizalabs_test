package br.com.luizalabs.gistvisualizer.presentation.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.luizalabs.gistvisualizer.R
import br.com.luizalabs.gistvisualizer.di.loadGistDetailDependencies
import br.com.luizalabs.gistvisualizer.presentation.extentions.loadUri
import br.com.luizalabs.gistvisualizer.presentation.models.DetailErrorViewState
import br.com.luizalabs.gistvisualizer.presentation.models.DetailSuccessViewState
import br.com.luizalabs.gistvisualizer.presentation.models.GistItem
import br.com.luizalabs.gistvisualizer.presentation.viewmodels.GistDetailViewModel
import br.com.luizalabs.gistvisualizer.presentation.views.adapters.GistDetailFileAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.gist_detail_fragment_layout.*
import kotlinx.android.synthetic.main.gist_detail_fragment_layout.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


const val GIST_BUNDLE_EXTRA = "gist_item"

class GistDetailFragment : Fragment() {

    private val viewModel: GistDetailViewModel by viewModel()

    init {
        loadGistDetailDependencies()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<GistItem>(GIST_BUNDLE_EXTRA)?.let { gist ->
            viewModel.populate(gist)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.gist_detail_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.detailFilesList.isNestedScrollingEnabled = true

        viewModel.stateGistDetail.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is DetailSuccessViewState -> {
                    loadScreen(state.gist)
                }
                is DetailErrorViewState -> {
                    Snackbar.make(view, state.msgRes, Snackbar.LENGTH_LONG)
                        .setAction("Ok", null)
                        .show()
                }
            }
        })
    }

    private fun loadScreen(gistItem: GistItem) {
        detailUserAvatar.loadUri(gistItem.avatarUri)
        detailUserName.setText(gistItem.ownerName)
        detailCreateDate.setText(gistItem.creationDate)
        detailDescription.setText(gistItem.description)
        detailFilesList.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = GistDetailFileAdapter(gistItem.files){ uri ->
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(browserIntent)
            }
        }

        detailFavButton.setOnClickListener{
            viewModel.favorite()
        }

        view?.let {
            when{
                gistItem.favorite -> it.detailFavButton.setColorFilter(
                    it.context.resources.getColor(
                        R.color.fav_color
                    )
                )
                else -> it.detailFavButton.setColorFilter(it.context.resources.getColor(R.color.unfav_color))
            }
        }
    }
}

