package br.com.luizalabs.gistvisualizer.presentation.views

import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.luizalabs.gistvisualizer.R
import br.com.luizalabs.gistvisualizer.di.loadGistListDependencies
import br.com.luizalabs.gistvisualizer.presentation.extentions.addEndScrollListener
import br.com.luizalabs.gistvisualizer.presentation.models.ErrorState
import br.com.luizalabs.gistvisualizer.presentation.models.LoadingState
import br.com.luizalabs.gistvisualizer.presentation.models.SuccessState
import br.com.luizalabs.gistvisualizer.presentation.viewmodels.GistListViewModel
import br.com.luizalabs.gistvisualizer.presentation.views.adapters.GistItemAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.gist_list_fragment_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class GistListFragment : Fragment() {

    private val viewModel: GistListViewModel by viewModel()
    private val gistAdapter: GistItemAdapter by lazy { GistItemAdapter() }
    private var blockLoadMore = false
    private var loadding = false

    init {
        loadGistListDependencies()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getGists(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.gist_list_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gistAdapter.favoriteCallBack = {
            viewModel.addOrRemoveFavorite(it)
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

        gistRecyclerView.addEndScrollListener {
            if (!blockLoadMore) {
                viewModel.getGists()
            }
        }

        viewModel.stateGistList.observe(viewLifecycleOwner, { state ->
            when (state) {
                is SuccessState -> {
                    showLoading(state.loading)
                    gistAdapter.gistItemList = state.gistList
                    gistAdapter.notifyDataSetChanged()
                }
                is ErrorState -> {
                    showLoading(state.loading)
                    Snackbar.make(view, state.msgRes, Snackbar.LENGTH_LONG)
                        .setAction("Ok", null)
                        .show()
                }
                is LoadingState -> {
                    loadding = state.loading
                    showLoading(loadding)
                }
            }
        })
    }

    private fun showLoading(show: Boolean) {
        gistProgress.visibility = if (show) VISIBLE else GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.action_favorites -> {
                view?.let {
                    val navController = Navigation.findNavController(it)
                    navController.navigate(R.id.action_gistListFragment_to_favoriteGistsFragment)
                }
            }
        }

        return false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search_menu, menu)

        val searchItem: MenuItem? = menu.findItem(R.id.action_search)
        val searchView: SearchView? = searchItem?.actionView as SearchView

        searchView?.queryHint = activity?.resources?.getString(R.string.gis_search_hint)

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.searchGistsByUserName(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            }
        )

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                    blockLoadMore = true
                    return true
                }

                override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                    blockLoadMore = false
                    viewModel.backToPage()
                    return true
                }
            }
        )
    }
}