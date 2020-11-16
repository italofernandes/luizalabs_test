package br.com.luizalabs.gistvisualizer.di

import androidx.annotation.VisibleForTesting
import br.com.luizalabs.gistvisualizer.presentation.viewmodels.FavoriteGistsViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val gistFavoritesModule = module {
    viewModel { FavoriteGistsViewModel(Dispatchers.IO, get(), get(), get(), get()) }
}

@VisibleForTesting
private val gistFavoritesViewModelModule by lazy { loadKoinModules(listOf(gistFavoritesModule)) }
internal fun loadGistFavoritesDependencies() = gistFavoritesViewModelModule