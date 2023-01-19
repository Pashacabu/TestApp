package com.pashacabu.testapp.domain.network

import com.pashacabu.testapp.data.MovieResponse
import retrofit2.Response
import retrofit2.http.GET

interface MoviesListApi {

    @GET("movie")
    suspend fun getMoviesList(): Response<MovieResponse>

}