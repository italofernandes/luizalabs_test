package br.com.luizalabs.gistvisualizer.di

import br.com.luizalabs.gistvisualizer.domain.usecases.*
import org.koin.dsl.module

val getAllGistsUseCaseModule = module {
    single { GetAllGistsUseCase(get()) }
}

val favoriteGistsUseCaseModule = module {
    factory { FavoriteGistUseCase(get()) }
}

val getAllFavoriteGistsUseCaseModule = module {
    single { GetAllFavoriteGistsUseCase(get()) }
}

val searchGistsByUserNameUseCaseModule = module {
    single { SearchGistsByNameUserCase(get()) }
}