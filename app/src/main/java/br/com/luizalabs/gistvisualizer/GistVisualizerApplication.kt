package br.com.luizalabs.gistvisualizer

import android.app.Application
import br.com.luizalabs.gistvisualizer.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GistVisualizerApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GistVisualizerApplication)

            modules(
                retrofitModule,
                dataBaseModule,
                convertersModule,
                repoSourceModule,
                getAllGistsUseCaseModule,
                favoriteGistsUseCaseModule,
                getAllFavoriteGistsUseCaseModule,
                searchGistsByUserNameUseCaseModule
            )
        }
    }
}