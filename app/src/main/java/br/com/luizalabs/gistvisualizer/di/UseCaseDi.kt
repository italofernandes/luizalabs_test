package br.com.luizalabs.gistvisualizer.di

import br.com.luizalabs.gistvisualizer.domain.entities.Gist
import br.com.luizalabs.gistvisualizer.domain.usecases.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val GET_ALL_GISTS_USE_CASE = "GetAllGistsUseCase"
internal const val FAVORITE_GISTS_USE_CASE = "FavoriteGistUseCase"
internal const val GET_ALL_FAVORITE_GISTS_USE_CASE = "GetAllFavoriteGistsUseCase"
internal const val SEARCH_GISTS_USE_CASE = "SearchGistsByNameUserCase"


val getAllGistsUseCaseModule = module {
    single<UseCaseWithParams<List<Gist>>>(named(GET_ALL_GISTS_USE_CASE)) { GetAllGistsUseCase(get()) }
}

val favoriteGistsUseCaseModule = module {
    factory<UseCaseWithParams<Unit>>(named(FAVORITE_GISTS_USE_CASE)) { FavoriteGistUseCase(get()) }
}

val getAllFavoriteGistsUseCaseModule = module {
    single<UseCase<List<Gist>>>(named(GET_ALL_FAVORITE_GISTS_USE_CASE)) { GetAllFavoriteGistsUseCase(get()) }
}

val searchGistsByUserNameUseCaseModule = module {
    single<UseCaseWithParams<List<Gist>>>(named(SEARCH_GISTS_USE_CASE)) { SearchGistsByNameUserCase(get()) }
}