package br.com.luizalabs.gistvisualizer.di

import androidx.annotation.VisibleForTesting
import br.com.luizalabs.gistvisualizer.data.service.converters.ResponseToGistConverter
import br.com.luizalabs.gistvisualizer.presentation.converters.GistItemToGistConverter
import br.com.luizalabs.gistvisualizer.presentation.converters.ListGistInListGistItemConverter
import br.com.luizalabs.gistvisualizer.presentation.viewmodels.GistDetailViewModel
import br.com.luizalabs.gistvisualizer.presentation.viewmodels.GistListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

val gistDetailModule = module {
    viewModel { GistDetailViewModel(Dispatchers.IO, get(named(GIST_ITEM_TO_GIST_CONVERTER)), get(named(FAVORITE_GISTS_USE_CASE))) }
}

@VisibleForTesting
private val gistDetailViewModelModule by lazy { loadKoinModules(listOf(gistDetailModule)) }
internal fun loadGistDetailDependencies() = gistDetailViewModelModule