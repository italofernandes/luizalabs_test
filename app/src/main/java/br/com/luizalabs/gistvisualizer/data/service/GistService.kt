package br.com.luizalabs.gistvisualizer.data.service

import br.com.luizalabs.gistvisualizer.data.service.entities.GistResponseModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GistService {
    @Headers("Authorization: token b7b97573a9bc20b44ed0ea6c45b241bbb57c9c37 ")
    @GET("/gists/public")
    suspend fun getGistsByPage(
            @Query("page") page: Int,
            @Query("per_page") perPage: Int = 100
    ):List<GistResponseModel>

    @Headers("Authorization: token b7b97573a9bc20b44ed0ea6c45b241bbb57c9c37 ")
    @GET("/users/{username}/gists")
    suspend fun searchGistsByUser(@Path("username") name: String):List<GistResponseModel>
}