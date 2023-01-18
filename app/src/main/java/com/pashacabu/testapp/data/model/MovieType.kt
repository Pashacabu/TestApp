package com.pashacabu.testapp.data.model

sealed class MovieType(val value: String) {

    object Movie : MovieType("movie")
    object TvSeries : MovieType("tv-series")
    object Cartoon : MovieType("cartoon")
    object Anime : MovieType("anime")
    object AnimatedSeries : MovieType("animated-series")
    object TvShow : MovieType("tv-show")

    companion object {
        fun get(value: String?): MovieType? {
            return when (value) {
                Movie.value -> Movie
                TvSeries.value -> TvSeries
                Cartoon.value -> Cartoon
                Anime.value -> Anime
                AnimatedSeries.value -> AnimatedSeries
                TvShow.value -> TvShow
                else -> null
            }
        }
    }
}
