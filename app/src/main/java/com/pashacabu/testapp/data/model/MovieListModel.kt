package com.pashacabu.testapp.data.model

data class MovieListModel(
    val movies: List<MovieItemModel> = listOf(),
    val page: Int? = null,
    val totalPages: Int? = null
)
