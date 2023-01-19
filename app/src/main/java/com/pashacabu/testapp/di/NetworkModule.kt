package com.pashacabu.testapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.pashacabu.testapp.domain.network.ApiErrorHandler
import com.pashacabu.testapp.domain.network.ApiErrorHandlerImpl
import com.pashacabu.testapp.domain.network.KinopoiskAuthInterceptor
import com.pashacabu.testapp.domain.network.QueryInterceptor
import com.pashacabu.testapp.domain.network.QueryInterceptorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideAuthInterceptor(): KinopoiskAuthInterceptor =
        KinopoiskAuthInterceptor()

    @Provides
    @Singleton
    fun provideQueryInterceptor(): QueryInterceptor =
        QueryInterceptorImpl()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        kinopoiskAuthInterceptor: KinopoiskAuthInterceptor,
        queryInterceptor: QueryInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(kinopoiskAuthInterceptor)
            .addInterceptor(queryInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()


    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .setLenient()
            .create()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        gson: Gson
    ): Retrofit.Builder =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://api.kinopoisk.dev/")

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        retrofitBuilder: Retrofit.Builder
    ): Retrofit =
        retrofitBuilder.client(okHttpClient).build()

    @Provides
    @Singleton
    fun provideApiErrorHandler(): ApiErrorHandler = ApiErrorHandlerImpl()

}