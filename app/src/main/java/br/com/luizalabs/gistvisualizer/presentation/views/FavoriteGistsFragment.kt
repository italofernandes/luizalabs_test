package br.com.luizalabs.gistvisualizer.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.luizalabs.gistvisualizer.R
import br.com.luizalabs.gistvisualizer.di.loadGistFavoritesDependencies
import br.com.luizalabs.gistvisualizer.presentation.models.ErrorState
import br.com.luizalabs.gistvisualizer.presentation.models.SuccessState
import br.com.luizalabs.gistvisualizer.presentation.viewmodels.FavoriteGistsViewModel
import br.com.luizalabs.gistvisualizer.presentation.views.adapters.GistItemAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.gist_list_fragment_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteGistsFragment() : Fragment(){

    private val viewModel: FavoriteGistsViewModel by viewModel()
    private val gistAdapter: GistItemAdapter by lazy { GistItemAdapter() }

    init {
        loadGistFavoritesDependencies()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.showFavorites()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_gists_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gistAdapter.favoriteCallBack = {
            viewModel.removeFavorite(it)
        }

        gistAdapter.clickCallBack = {gist ->
            val bundle = bundleOf(GIST_BUNDLE_EXTRA to gist)
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_gistListFragment_to_gistDetailFragment, bundle)
        }

        gistRecyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = gistAdapter
        }

        viewModel.stateGistList.observe(viewLifecycleOwner, { state ->
            when (state) {
                is SuccessState -> {
                    gistAdapter.gistItemList = state.gistList
                    gistAdapter.notifyDataSetChanged()
                }
                is ErrorState -> {
                    Snackbar.make(view, state.msgRes, Snackbar.LENGTH_LONG)
                        .setAction("Ok", null)
                        .show()
                }
                else -> { }
            }
        })
    }
}