package com.pashacabu.testapp.domain.repository

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import com.pashacabu.testapp.data.model.ApiField
import com.pashacabu.testapp.data.model.ApiQuery
import com.pashacabu.testapp.data.model.MovieItemModel
import com.pashacabu.testapp.data.model.MovieListModel
import com.pashacabu.testapp.domain.interactors.MoviesListInteractor
import com.pashacabu.testapp.domain.repository.MoviesListRepository.Companion.DEFAULT_LIMIT_PER_PAGE
import com.pashacabu.testapp.domain.utils.isSuccessful
import com.pashacabu.testapp.extensions.asState
import timber.log.Timber
import java.util.Calendar

interface MoviesListRepository {

    val movies: State<List<MovieItemModel>>

    suspend fun loadMovies(reload: Boolean = false)
    suspend fun searchMovies(reload: Boolean = false, field: ApiField, cond: String)
    fun disableSearch()
    suspend fun reloadMovies()
    suspend fun filterMovies()

    companion object {
        const val DEFAULT_LIMIT_PER_PAGE = 20
    }
}

class MoviesListRepositoryImpl(
    private val moviesListInteractor: MoviesListInteractor
) : MoviesListRepository {
    private val _movies = mutableStateOf<List<MovieItemModel>>(listOf())
    override val movies = _movies.asState()

    private val defaultList = mutableStateOf(MovieListModel())
    private val defaultQuery = mutableStateOf(
        ApiQuery.constructQuery(
            page = 0,
            limitPerPage = DEFAULT_LIMIT_PER_PAGE,
            searchField = ApiField.YEAR,
            searchCondition = Calendar.getInstance().get(Calendar.YEAR).toString(),
            sortField = ApiField.Rating.KP,
            sortOrder = ApiQuery.Paired.SortOrder.Desc
        )
    )
    private val searchList = mutableStateOf(MovieListModel())
    private val searchQuery = mutableStateOf<List<ApiQuery>>(listOf())

    private val searchEnabled = mutableStateOf(false)

    override suspend fun loadMovies(reload: Boolean) {
        val currentPage = if (searchEnabled.value) searchList.value.page else defaultList.value.page
        val totalPages =
            if (searchEnabled.value) searchList.value.totalPages else defaultList.value.totalPages
        val page = if (currentPage == null || reload) 1 else
            if (currentPage < (totalPages ?: 3) - 1) currentPage + 1 else null
        page?.let {
            val query = (if (searchEnabled.value) searchQuery.value else defaultQuery.value )
                .map {
                    if (it is ApiQuery.Page) ApiQuery.Page(page) else it
                }
            val res = moviesListInteractor.getList(query)
            if (res.isSuccessful()) {
                res.data?.let {
                    updateResultsAndQueries(it, reload, query)
                }
            }
        }
    }

    private fun updateResultsAndQueries(result: MovieListModel, reload: Boolean, query: List<ApiQuery>){
        if (searchEnabled.value) {
            if (reload) {
                searchList.value = result
            } else {
                searchList.value = result.copy(movies = searchList.value.movies + result.movies)
            }
            searchQuery.value = query
            _movies.value = searchList.value.movies
        } else {
            if (reload) {
                defaultList.value = result
            } else {
                defaultList.value = result.copy(movies = defaultList.value.movies + result.movies)
            }
            defaultQuery.value = query
            _movies.value = defaultList.value.movies
        }
    }

    override suspend fun searchMovies(reload: Boolean, field: ApiField, cond: String) {
        searchEnabled.value = true
        if (reload) {
            loadMovies(true)
        } else {
            val fieldTheSame = field == searchQuery.value.filterIsInstance<ApiQuery.Paired.Search>().firstOrNull()?.field
            val condTheSame = cond == searchQuery.value.filterIsInstance<ApiQuery.Paired.Search>().firstOrNull()?.condition
            if (fieldTheSame && condTheSame){
                loadMovies(false)
            } else {
                searchQuery.value = ApiQuery.constructQuery(
                    searchField = field,
                    searchCondition = cond,
                    page = 1,
                    limitPerPage = DEFAULT_LIMIT_PER_PAGE
                )
                loadMovies(true)
            }
        }
    }

    override fun disableSearch() {
        searchEnabled.value = false
        searchList.value = MovieListModel()
    }

    override suspend fun reloadMovies() {
        TODO("Not yet implemented")
    }

    override suspend fun filterMovies() {
        TODO("Not yet implemented")
    }

}