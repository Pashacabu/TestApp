package com.pashacabu.testapp.di

import com.pashacabu.testapp.domain.interactors.MoviesListInteractor
import com.pashacabu.testapp.domain.repository.MoviesListRepository
import com.pashacabu.testapp.domain.repository.MoviesListRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMoviesListRepo(
        moviesListInteractor: MoviesListInteractor
    ): MoviesListRepository =
        MoviesListRepositoryImpl(moviesListInteractor)
}