package com.whyraya.moviedb.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.whyraya.moviedb.data.remote.MovieRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val dataMapper: MovieDataMapper
): MovieRepository {

    override fun getMovie(): Flow<PagingData<MovieDto>> = remoteDataSource.getMovies().map {
        it.map { response ->
            dataMapper.mapMovieToDto(response)
        }
    }
}
