package com.whyraya.moviedb.domain

import com.whyraya.moviedb.data.model.MovieResponse
import javax.inject.Inject

class MovieDataMapper @Inject constructor() {

    fun mapMovieToDto(response: MovieResponse?) = response?.run {
        MovieDto(
            id = id.orZero(),
            firstAirDate = firstAirDate.orEmpty(),
            name = name.orEmpty(),
            originalTitle = originalTitle.orEmpty(),
            originalLanguage = originalLanguage.orEmpty(),
            overview = overview.orEmpty(),
            posterPath = posterPath.orEmpty(),
            voteAverage = voteAverage.orZero(),
            voteCount = voteCount.orZero()
        )
    } ?: MovieDto()
}

fun Int?.orZero(): Int = this ?: 0

fun Long?.orZero(): Long = this ?: 0L

fun Float?.orZero(): Float = this ?: 0F

fun Double?.orZero(): Double = this ?: 0.0
