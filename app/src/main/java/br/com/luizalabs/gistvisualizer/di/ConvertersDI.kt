package br.com.luizalabs.gistvisualizer.di

import br.com.luizalabs.gistvisualizer.data.db.converters.GistModelToGistConverter
import br.com.luizalabs.gistvisualizer.data.db.converters.GistToGistModelConverter
import br.com.luizalabs.gistvisualizer.data.service.converters.ResponseToGistConverter
import br.com.luizalabs.gistvisualizer.presentation.converters.GistItemToGistConverter
import br.com.luizalabs.gistvisualizer.presentation.converters.ListGistInListGistItemConverter
import org.koin.dsl.module

val convertersModule = module {
    single { GistToGistModelConverter() }
    single { GistModelToGistConverter() }
    single { ResponseToGistConverter(get()) }
    single { GistItemToGistConverter() }
    single { ListGistInListGistItemConverter() }
}