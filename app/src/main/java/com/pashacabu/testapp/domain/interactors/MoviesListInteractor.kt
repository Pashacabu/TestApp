package com.pashacabu.testapp.domain.interactors

import com.pashacabu.testapp.data.model.MovieListModel
import com.pashacabu.testapp.domain.mappers.MovieResponseToMovieListModelApiMapper
import com.pashacabu.testapp.domain.network.MoviesListApi
import com.pashacabu.testapp.domain.utils.ApiResult

interface MoviesListInteractor {

    suspend fun getList(): ApiResult<MovieListModel>
}

class MoviesListInteractorImpl(
    private val api: MoviesListApi,
    private val movieResponseToMovieListModelMapper: MovieResponseToMovieListModelApiMapper
) : MoviesListInteractor {
    override suspend fun getList(): ApiResult<MovieListModel> {
        return movieResponseToMovieListModelMapper.transform(
            api.getMoviesList()
        )
    }

}