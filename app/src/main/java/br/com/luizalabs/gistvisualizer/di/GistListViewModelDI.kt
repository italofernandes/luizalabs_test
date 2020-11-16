package br.com.luizalabs.gistvisualizer.di

import androidx.annotation.VisibleForTesting
import br.com.luizalabs.gistvisualizer.presentation.viewmodels.GistListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

val gistListModule = module {
    viewModel {
        GistListViewModel(
                Dispatchers.IO,
                get(named(LIST_GIST_TO_LIST_GIST_ITEM_CONVERTER)),
                get(named(GIST_ITEM_TO_GIST_CONVERTER)),
                get(named(GET_ALL_GISTS_USE_CASE)),
                get(named(FAVORITE_GISTS_USE_CASE)),
                get(named(SEARCH_GISTS_USE_CASE))
        )
    }
}

@VisibleForTesting
private val gistListViewModelModule by lazy { loadKoinModules(listOf(gistListModule)) }
internal fun loadGistListDependencies() = gistListViewModelModule