package com.whyraya.moviedb.data.remote

import com.whyraya.moviedb.data.model.MovieResponse
import com.whyraya.moviedb.data.remote.paging.BasePagingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieServices {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("page") page: Int = 0,
    ): Response<BasePagingResponse<List<MovieResponse>>>
}
