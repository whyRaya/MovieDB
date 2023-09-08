package com.whyraya.moviedb.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDto(
    val id: Int = 0,
    val firstAirDate: String = "",
    val name: String = "",
    val originalTitle: String = "",
    val originalLanguage: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0,
) : Parcelable
