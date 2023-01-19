package com.pashacabu.testapp.domain.interactors

import com.pashacabu.testapp.data.model.ApiQuery
import com.pashacabu.testapp.data.model.MovieListModel
import com.pashacabu.testapp.domain.mappers.MovieResponseToMovieListModelApiMapper
import com.pashacabu.testapp.domain.network.ApiErrorHandler
import com.pashacabu.testapp.domain.network.MoviesListApi
import com.pashacabu.testapp.domain.network.QueryInterceptor
import com.pashacabu.testapp.domain.utils.ApiResult

interface MoviesListInteractor {

    suspend fun getList(queryParams: List<ApiQuery> = listOf()): ApiResult<MovieListModel>
}

class MoviesListInteractorImpl(
    private val api: MoviesListApi,
    private val apiErrorHandler: ApiErrorHandler,
    private val movieResponseToMovieListModelMapper: MovieResponseToMovieListModelApiMapper,
    private val queryInterceptor: QueryInterceptor
) : MoviesListInteractor {
    override suspend fun getList(queryParams: List<ApiQuery>): ApiResult<MovieListModel> {
        queryInterceptor.setQueries(queryParams)
        return apiErrorHandler.safeCall {
            movieResponseToMovieListModelMapper.transform(
                api.getMoviesList()
            ).also { queryInterceptor.clearQueries() }
        }
    }

}