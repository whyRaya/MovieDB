package com.whyraya.moviedb.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailDto(
    val adult: Boolean = false,
    val backdropPath: String = "",
    val budget: Int = 0,
    val homepage: String = "",
    val id: Int = 0,
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val posterPath: String = "",
    val releaseDate: String = "",
    val revenue: Double = 0.0,
    val runtime: Int = 0,
    val status: String = "",
    val tagline: String = "",
    val title: String = "",
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0,
    val genres: List<MovieGenreDto> = emptyList(),
    val videos: List<MovieVideosDto> = emptyList()
) : Parcelable
