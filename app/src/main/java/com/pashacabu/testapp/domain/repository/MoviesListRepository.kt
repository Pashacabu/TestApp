package com.pashacabu.testapp.domain.repository

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.pashacabu.testapp.data.model.ApiQuery
import com.pashacabu.testapp.data.model.MovieListModel
import com.pashacabu.testapp.domain.interactors.MoviesListInteractor
import com.pashacabu.testapp.domain.utils.isSuccessful
import com.pashacabu.testapp.extensions.asState
import timber.log.Timber

interface MoviesListRepository {

    val movies: State<MovieListModel>

    suspend fun loadMovies()
    suspend fun searchMovies()
    suspend fun reloadMovies()
    suspend fun filterMovies()
}

class MoviesListRepositoryImpl(
    private val moviesListInteractor: MoviesListInteractor
) : MoviesListRepository {
    private val _movies = mutableStateOf(MovieListModel())
    override val movies = _movies.asState()

    override suspend fun loadMovies() {
        val res = moviesListInteractor.getList(
            listOf(
                ApiQuery.Limit(1000),
                ApiQuery.Paired.Search(
                    field = "year",
                    condition = 2022
                ),
                ApiQuery.Paired.Sort(
                    field = "rating.imdb",
                    order = ApiQuery.Paired.SortOrder.Desc
                )
            )
        )
        if (res.isSuccessful()) {
            res.data?.let {
                _movies.value = it
                it.movies.forEach {
                    Timber.d("MovieItem = $it")
                }
            }
        }
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