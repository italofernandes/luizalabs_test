package br.com.luizalabs.gistvisualizer.di

import androidx.room.Room
import br.com.luizalabs.gistvisualizer.data.db.AppDataBase
import br.com.luizalabs.gistvisualizer.data.db.FavoriteGistDataSourceImpl
import br.com.luizalabs.gistvisualizer.data.db.converters.GistModelToGistConverter
import br.com.luizalabs.gistvisualizer.data.db.converters.GistToGistModelConverter
import br.com.luizalabs.gistvisualizer.data.db.daos.GistDao
import br.com.luizalabs.gistvisualizer.data.db.daos.GistFileDao
import br.com.luizalabs.gistvisualizer.data.service.GistService
import br.com.luizalabs.gistvisualizer.data.service.GistServiceDataSourceImpl
import br.com.luizalabs.gistvisualizer.data.service.converters.ResponseToGistConverter
import br.com.luizalabs.gistvisualizer.domain.data.datasource.FavoriteGistDataSource
import br.com.luizalabs.gistvisualizer.domain.data.datasource.GistServiceDataSource
import br.com.luizalabs.gistvisualizer.domain.data.repository.GistRepository
import br.com.luizalabs.gistvisualizer.domain.data.repository.GistRepositoryImpl
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
val retrofitModule = module {
    single { GsonBuilder().setLenient().create() }
    single<GistService> {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .cache(null)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()

        retrofit.create(GistService::class.java)
    }
}

val dataBaseModule = module {
    single { Room.databaseBuilder(
        androidApplication(),
        AppDataBase::class.java, "notes_data_base"
    ).build() }

    factory<GistDao> { get<AppDataBase>().getGistDao() }
    factory<GistFileDao> { get<AppDataBase>().getGistFileDao() }
}

val repoSourceModule = module {
    single<GistServiceDataSource> { GistServiceDataSourceImpl(get(), get(named(RESPONSE_GIST_TO_GIST_CONVERTER))) }
    single<FavoriteGistDataSource> { FavoriteGistDataSourceImpl(get(), get(), get(named(GIST_TO_GIST_MODEL_CONVERTER)), get(named(GIST_MODEL_TO_GIST_CONVERTER))) }
    single<GistRepository> { GistRepositoryImpl(get(), get()) }
}



