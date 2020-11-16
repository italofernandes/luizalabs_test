package br.com.luizalabs.gistvisualizer.di

import androidx.annotation.VisibleForTesting
import br.com.luizalabs.gistvisualizer.presentation.viewmodels.FavoriteGistsViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

val gistFavoritesModule = module {
    viewModel { FavoriteGistsViewModel(Dispatchers.IO, get(named(FAVORITE_GISTS_USE_CASE)), get(named(GET_ALL_FAVORITE_GISTS_USE_CASE)), get(named(LIST_GIST_TO_LIST_GIST_ITEM_CONVERTER)), get(named(GIST_ITEM_TO_GIST_CONVERTER))) }
}

@VisibleForTesting
private val gistFavoritesViewModelModule by lazy { loadKoinModules(listOf(gistFavoritesModule)) }
internal fun loadGistFavoritesDependencies() = gistFavoritesViewModelModule