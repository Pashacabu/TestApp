package com.pashacabu.testapp.domain.mappers

import com.pashacabu.testapp.data.MovieResponse
import com.pashacabu.testapp.data.model.MovieItemModel
import com.pashacabu.testapp.data.model.MovieListModel
import com.pashacabu.testapp.data.model.MovieType
import com.pashacabu.testapp.data.model.RatingModel
import com.pashacabu.testapp.domain.utils.ApiResult
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieResponseToMovieListModelApiMapper @Inject constructor() :
    ApiMapper<Response<MovieResponse>, ApiResult<MovieListModel>> {
    override fun transform(from: Response<MovieResponse>): ApiResult<MovieListModel> {
        return if (from.isSuccessful) {
            ApiResult.Success(
                data = MovieListModel(
                    movies = from.body()?.movies?.map {
                        Timber.d("MovieResponse = $it")
                        MovieItemModel(
                            id = it?.id,
                            name = it?.names?.firstOrNull()?.name,
                            year = it?.year,
                            type = MovieType.get(it?.type),
                            rating = RatingModel(
                                imdb = it?.rating?.imdb,
                                kp = it?.rating?.kp
                            )
                        )
                    } ?: listOf(),
                    page = from.body()?.page,
                    totalPages = from.body()?.total
                ),
                code = from.code()
            )
        } else {
            ApiResult.Error(code = from.code())
        }
    }
}