package com.whyraya.moviedb.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MovieResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("release_date") val firstAirDate: String? = null,
    @SerializedName("title") val name: String? = null,
    @SerializedName("original_title") val originalTitle: String? = null,
    @SerializedName("original_language") val originalLanguage: String? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("vote_average") val voteAverage: Double? = null,
    @SerializedName("vote_count") val voteCount: Int? = null,
)
