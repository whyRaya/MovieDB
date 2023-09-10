package com.whyraya.moviedb.data.remote

import com.whyraya.moviedb.data.model.MovieDetailResponse
import com.whyraya.moviedb.data.model.MovieResponse
import com.whyraya.moviedb.data.model.MovieReviewResponse
import com.whyraya.moviedb.data.model.MovieVideosResponse
import com.whyraya.moviedb.data.remote.paging.BasePagingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieServices {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("page") page: Int = 0
    ): Response<BasePagingResponse<List<MovieResponse>>>

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(
        @Path("movieId") movieId: Int
    ): Response<MovieDetailResponse>

    @GET("movie/{movieId}/videos")
    suspend fun getMovieVideos(
        @Path("movieId") movieId: Int
    ): Response<MovieVideosResponse>

    @GET("movie/{movieId}/reviews")
    suspend fun getMovieReviews(
        @Path("movieId") movieId: Int,
        @Query("page") page: Int = 0
    ): Response<BasePagingResponse<List<MovieReviewResponse>>>

}
