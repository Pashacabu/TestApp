package com.pashacabu.testapp.domain.repository

interface MoviesListRepository {

    val movies: List<Any>

    suspend fun loadMovies()
    suspend fun searchMovies()
    suspend fun reloadMovies()
    suspend fun filterMovies()
}

class MoviesListRepositoryImpl(): MoviesListRepository {
    override val movies: List<Any>
        get() = TODO("Not yet implemented")

    override suspend fun loadMovies() {
        TODO("Not yet implemented")
    }

    override suspend fun searchMovies() {
        TODO("Not yet implemented")
    }

    override suspend fun reloadMovies() {
        TODO("Not yet implemented")
    }

    override suspend fun filterMovies() {
        TODO("Not yet implemented")
    }

}