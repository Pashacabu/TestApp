package com.pashacabu.testapp.di

import com.pashacabu.testapp.domain.network.MoviesListApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideMoviesListApi(
        retrofit: Retrofit
    ): MoviesListApi = retrofit.create(MoviesListApi::class.java)

}