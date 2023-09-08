package com.whyraya.moviedb.data.remote

import androidx.paging.PagingData
import com.whyraya.moviedb.data.model.MovieResponse
import com.whyraya.moviedb.data.remote.paging.withPager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(private val movieServices: MovieServices) {

    fun getMovies(): Flow<PagingData<MovieResponse>> = withPager { page ->
        movieServices.getMovies(page).body()?.results.orEmpty()
    }.flow
}
