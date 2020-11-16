package br.com.luizalabs.gistvisualizer.di

import br.com.luizalabs.gistvisualizer.commons.BaseConverter
import br.com.luizalabs.gistvisualizer.data.db.converters.GistModelToGistConverter
import br.com.luizalabs.gistvisualizer.data.db.converters.GistToGistModelConverter
import br.com.luizalabs.gistvisualizer.data.db.entities.GistWithOwnerAndFile
import br.com.luizalabs.gistvisualizer.data.service.converters.ResponseToGistConverter
import br.com.luizalabs.gistvisualizer.data.service.entities.GistResponseModel
import br.com.luizalabs.gistvisualizer.domain.entities.Gist
import br.com.luizalabs.gistvisualizer.presentation.converters.GistItemToGistConverter
import br.com.luizalabs.gistvisualizer.presentation.converters.ListGistInListGistItemConverter
import br.com.luizalabs.gistvisualizer.presentation.models.GistItem
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val GIST_TO_GIST_MODEL_CONVERTER = "GistToGistModelConverter"
internal const val GIST_MODEL_TO_GIST_CONVERTER = "GistModelToGistConverter"
internal const val RESPONSE_GIST_TO_GIST_CONVERTER = "ResponseToGistConverter"
internal const val GIST_ITEM_TO_GIST_CONVERTER = "GistItemToGistConverter"
internal const val LIST_GIST_TO_LIST_GIST_ITEM_CONVERTER = "ListGistInListGistItemConverter"

val convertersModule = module {
    single<BaseConverter<GistWithOwnerAndFile, Gist>>(named(GIST_TO_GIST_MODEL_CONVERTER)) { GistToGistModelConverter() }
    single<BaseConverter<Gist, GistWithOwnerAndFile>>(named(GIST_MODEL_TO_GIST_CONVERTER)) { GistModelToGistConverter() }
    single<BaseConverter<List<GistResponseModel>, List<Gist>>>(named(RESPONSE_GIST_TO_GIST_CONVERTER)) { ResponseToGistConverter(get()) }
    single<BaseConverter<GistItem, Gist>>(named(GIST_ITEM_TO_GIST_CONVERTER)) { GistItemToGistConverter() }
    single<BaseConverter<List<Gist>, List<GistItem>>>(named(LIST_GIST_TO_LIST_GIST_ITEM_CONVERTER)) { ListGistInListGistItemConverter() }
}