package com.pashacabu.testapp.data.model

data class MovieItemModel(
    val id: Int? = null,
    val name: String? = null,
    val type: MovieType? = null,
    val year: Int? = null,
    val rating: RatingModel? = null
)
