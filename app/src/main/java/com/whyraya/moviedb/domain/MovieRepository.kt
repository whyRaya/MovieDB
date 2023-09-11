package com.whyraya.moviedb.domain

import androidx.paging.PagingData
import com.whyraya.moviedb.domain.model.MovieDetailDto
import com.whyraya.moviedb.domain.model.MovieDto
import com.whyraya.moviedb.domain.model.MovieGenreDto
import com.whyraya.moviedb.domain.model.MovieReviewDto
import com.whyraya.moviedb.domain.model.MovieVideosDto
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovieGenres(): Flow<List<MovieGenreDto>>

    fun getMovie(genreId: Int): Flow<PagingData<MovieDto>>

    fun getMovieDetail(movieId: Int): Flow<MovieDetailDto>

    fun getMovieVideos(movieId: Int): Flow<List<MovieVideosDto>>

    fun getMovieReview(movieId: Int): Flow<PagingData<MovieReviewDto>>
}
