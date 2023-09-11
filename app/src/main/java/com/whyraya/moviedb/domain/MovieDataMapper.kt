package com.whyraya.moviedb.domain

import com.whyraya.moviedb.data.model.MovieDetailResponse
import com.whyraya.moviedb.data.model.MovieGenreResponse
import com.whyraya.moviedb.data.model.MovieResponse
import com.whyraya.moviedb.data.model.MovieReviewResponse
import com.whyraya.moviedb.data.model.MovieVideosResponse
import com.whyraya.moviedb.domain.model.MovieDetailDto
import com.whyraya.moviedb.domain.model.MovieGenreDto
import com.whyraya.moviedb.domain.model.MovieReviewDto
import com.whyraya.moviedb.domain.model.MovieVideosDto
import com.whyraya.moviedb.utils.formatUtc
import com.whyraya.moviedb.utils.formatYear
import com.whyraya.moviedb.utils.orFalse
import com.whyraya.moviedb.utils.orZero
import com.whyraya.moviedb.utils.roundDecimal
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
            posterPath = posterPath.orEmpty().toImageUrl(),
            voteAverage = voteAverage.orZero().roundDecimal(),
            voteCount = voteCount.orZero()
        )
    } ?: MovieDto()

    fun mapToVideoDetailDto(
        response: MovieDetailResponse?,
        videos: List<MovieVideosDto>
    ) = response?.run {
        MovieDetailDto(
            adult = adult.orFalse(),
            backdropPath = backdropPath.orEmpty().toBackdropUrl(),
            budget = budget.orZero(),
            homepage = homepage.orEmpty(),
            id = id.orZero(),
            originalLanguage = originalLanguage.orEmpty(),
            originalTitle = originalTitle.orEmpty(),
            overview = overview.orEmpty(),
            popularity = popularity.orZero(),
            posterPath = posterPath.orEmpty().toImageUrl(),
            releaseDate = releaseDate.orEmpty().formatYear(),
            revenue = revenue.orZero(),
            runtime = runtime.orZero(),
            status = status.orEmpty(),
            tagline = tagline.orEmpty(),
            title = title.orEmpty(),
            voteAverage = voteAverage.orZero().roundDecimal(),
            voteCount = voteCount.orZero(),
            genres = genres.orEmpty().map {
                MovieGenreDto(
                    id = it.id.orZero(),
                    name = it.name.orEmpty()
                )
            },
            videos = videos
        )
    } ?: MovieDetailDto()

    fun mapToVideoDto(response: MovieVideosResponse?) = response?.results?.map {
        MovieVideosDto(
            id = it.id.orEmpty(),
            iso31661 = it.iso31661.orEmpty(),
            iso6391 = it.iso6391.orEmpty(),
            key = it.key.orEmpty(),
            name = it.name.orEmpty(),
            official = it.official.orFalse(),
            publishedAt = it.publishedAt.orEmpty().formatUtc(),
            site = it.site.orEmpty(),
            size = it.size.orZero(),
            type = it.type.orEmpty(),
            thumbnail = it.key.orEmpty().toYoutubeThumbnailUrl()
        )
    } ?: emptyList()

    fun mapToReviewDto(response: MovieReviewResponse?) = response?.run {
        MovieReviewDto(
            author = author.orEmpty(),
            content = content.orEmpty(),
            createdAt = createdAt.orEmpty(),
            id = id.orEmpty(),
            updatedAt = updatedAt.orEmpty().formatUtc(),
            url = url.orEmpty(),
            avatarPath = authorDetails?.avatarPath.orEmpty().toImageUrl(),
            name = authorDetails?.avatarPath.orEmpty(),
            rating = authorDetails?.rating.orZero(),
            username = authorDetails?.username.orEmpty()
        )
    } ?: MovieReviewDto()

    fun mapToGenreDto(response: MovieGenreResponse?) = response?.genres.orEmpty().map {
        MovieGenreDto(
            id = it.id.orZero(),
            name = it.name.orEmpty()
        )
    }
}



fun String.toImageUrl() = "https://image.tmdb.org/t/p/w342$this"

fun String.toBackdropUrl() = "https://image.tmdb.org/t/p/original$this"

fun String.toYoutubeThumbnailUrl() = "https://img.youtube.com/vi/$this/0.jpg"

