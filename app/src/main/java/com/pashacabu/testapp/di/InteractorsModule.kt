package com.pashacabu.testapp.di

import com.pashacabu.testapp.domain.interactors.MoviesListInteractor
import com.pashacabu.testapp.domain.interactors.MoviesListInteractorImpl
import com.pashacabu.testapp.domain.mappers.MovieResponseToMovieListModelApiMapper
import com.pashacabu.testapp.domain.network.ApiErrorHandler
import com.pashacabu.testapp.domain.network.MoviesListApi
import com.pashacabu.testapp.domain.network.QueryInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class InteractorsModule {

    @Provides
    @Singleton
    fun provideMoviesListInteractor(
        api: MoviesListApi,
        apiErrorHandler: ApiErrorHandler,
        movieResponseToMovieListModelApiMapper: MovieResponseToMovieListModelApiMapper,
        queryInterceptor: QueryInterceptor
    ): MoviesListInteractor =
        MoviesListInteractorImpl(
            api,
            apiErrorHandler,
            movieResponseToMovieListModelApiMapper,
            queryInterceptor
        )
}