package com.pashacabu.testapp.data.model

sealed class ApiField(val value: String, val displayName: String) {

    object YEAR: ApiField("year", "Year")

    sealed class Rating(value: String, displayName: String): ApiField("$RATING.$value", "$displayName $RATING"){

        object KP: Rating("kp", "KP")
        object TMDB: Rating("tmdb", "TMDB")
        object IMDB: Rating("imdb", "IMDB")

        companion object {
            const val RATING = "rating"
        }
    }

    object NAME: ApiField("name", "Name")
    object TYPE: ApiField("type", "Type")


}
