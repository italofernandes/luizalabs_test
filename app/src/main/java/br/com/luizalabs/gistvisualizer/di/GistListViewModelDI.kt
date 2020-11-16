package br.com.luizalabs.gistvisualizer.di

import androidx.annotation.VisibleForTesting
import br.com.luizalabs.gistvisualizer.presentation.viewmodels.GistListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val gistListModule = module {
    viewModel { GistListViewModel(Dispatchers.IO, get(), get(), get(), get(), get()) }
}

@VisibleForTesting
private val gistListViewModelModule by lazy { loadKoinModules(listOf(gistListModule)) }
internal fun loadGistListDependencies() = gistListViewModelModule