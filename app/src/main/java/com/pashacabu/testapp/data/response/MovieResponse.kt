package com.pashacabu.testapp.data

import com.google.gson.annotations.SerializedName

data class MovieResponse(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("pages")
	val pages: Int? = null,

	@field:SerializedName("docs")
	val movies: List<MovieItem?>? = null,

	@field:SerializedName("limit")
	val limit: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null
)

data class Rating(

	@field:SerializedName("imdb")
	val imdb: Float? = null,

	@field:SerializedName("kp")
	val kp: Float? = null,

	@field:SerializedName("await")
	val await: Float? = null,

	@field:SerializedName("russianFilmCritics")
	val russianFilmCritics: Float? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("filmCritics")
	val filmCritics: Float? = null
)

data class MovieItem(

	@field:SerializedName("year")
	val year: Int? = null,

	@field:SerializedName("rating")
	val rating: Rating? = null,

	@field:SerializedName("externalId")
	val externalId: ExternalId? = null,

	@field:SerializedName("description")
	val description: Any? = null,

	@field:SerializedName("shortDescription")
	val shortDescription: Any? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("releaseYears")
	val releaseYears: List<Any?>? = null,

	@field:SerializedName("movieLength")
	val movieLength: Any? = null,

	@field:SerializedName("names")
	val names: List<NamesItem?>? = null,

	@field:SerializedName("enName")
	val enName: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("logo")
	val logo: Logo? = null,

	@field:SerializedName("votes")
	val votes: Votes? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("alternativeName")
	val alternativeName: String? = null,

	@field:SerializedName("poster")
	val poster: Any? = null,

	@field:SerializedName("watchability")
	val watchability: Watchability? = null
)

data class Logo(

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("url")
	val url: Any? = null
)

data class Watchability(

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("items")
	val items: Any? = null
)

data class Votes(

	@field:SerializedName("imdb")
	val imdb: Int? = null,

	@field:SerializedName("kp")
	val kp: Int? = null,

	@field:SerializedName("await")
	val await: Int? = null,

	@field:SerializedName("russianFilmCritics")
	val russianFilmCritics: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("filmCritics")
	val filmCritics: Int? = null
)

data class NamesItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("_id")
	val id: String? = null
)

data class ExternalId(

	@field:SerializedName("kpHD")
	val kpHD: Any? = null,

	@field:SerializedName("imdb")
	val imdb: String? = null,

	@field:SerializedName("_id")
	val id: String? = null
)

data class Poster(

	@field:SerializedName("previewUrl")
	val previewUrl: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)
