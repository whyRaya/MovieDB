package com.whyraya.moviedb.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.whyraya.moviedb.data.remote.MovieRemoteDataSource
import com.whyraya.moviedb.domain.mapper.MovieDataMapper
import com.whyraya.moviedb.domain.model.MovieDetailDto
import com.whyraya.moviedb.domain.model.MovieDto
import com.whyraya.moviedb.domain.model.MovieGenreDto
import com.whyraya.moviedb.domain.model.MovieReviewDto
import com.whyraya.moviedb.domain.model.MovieVideosDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val dataMapper: MovieDataMapper
) : MovieRepository {

    override fun getMovieGenres(): Flow<List<MovieGenreDto>> = flow {
        val response = remoteDataSource.getMovieGenres()
        emit(dataMapper.mapToGenreDto(response))
    }.flowOn(Dispatchers.IO)

    override fun getMovie(genreId: Int): Flow<PagingData<MovieDto>> =
        remoteDataSource.getMovies(genreId).map {
            it.map { response ->
                dataMapper.mapMovieToDto(response)
            }
        }

    override fun getMovieDetail(movieId: Int): Flow<MovieDetailDto> = flow {
        coroutineScope {
            val responseDetail = async { remoteDataSource.getMovieDetail(movieId) }
            val responseVideos = async { remoteDataSource.getMovieVideos(movieId) }
            val detail = dataMapper.mapToVideoDetailDto(
                responseDetail.await(),
                dataMapper.mapToVideoDto(responseVideos.await())
            )
            emit(detail)
        }
    }.flowOn(Dispatchers.IO)

    override fun getMovieVideos(movieId: Int): Flow<List<MovieVideosDto>> = flow {
        val response = remoteDataSource.getMovieVideos(movieId)
        emit(dataMapper.mapToVideoDto(response))
    }.flowOn(Dispatchers.IO)

    override fun getMovieReview(movieId: Int): Flow<PagingData<MovieReviewDto>> =
        remoteDataSource.getMovieReviews(movieId).map {
            it.map { response ->
                dataMapper.mapToReviewDto(response)
            }
        }
}
