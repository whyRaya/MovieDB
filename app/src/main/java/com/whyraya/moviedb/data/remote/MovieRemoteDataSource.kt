package com.whyraya.moviedb.data.remote

import androidx.paging.PagingData
import com.whyraya.moviedb.data.model.MovieGenreResponse
import com.whyraya.moviedb.data.model.MovieDetailResponse
import com.whyraya.moviedb.data.model.MovieResponse
import com.whyraya.moviedb.data.model.MovieReviewResponse
import com.whyraya.moviedb.data.model.MovieVideosResponse
import com.whyraya.moviedb.data.remote.paging.withPager
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(private val movieServices: MovieServices) {

    suspend fun getMovieGenres(): MovieGenreResponse = getResponse {
        movieServices.getMovieGenres()
    }

    fun getMovies(genreId: Int): Flow<PagingData<MovieResponse>> = withPager { page ->
        movieServices.getMovies(genreId, page).body()?.results.orEmpty()
    }.flow

    suspend fun getMovieDetail(movieId: Int): MovieDetailResponse = getResponse {
        movieServices.getMovieDetail(movieId)
    }

    suspend fun getMovieVideos(movieId: Int): MovieVideosResponse = getResponse {
        movieServices.getMovieVideos(movieId)
    }

    fun getMovieReviews(movieId: Int): Flow<PagingData<MovieReviewResponse>> = withPager { page ->
        movieServices.getMovieReviews(movieId, page).body()?.results.orEmpty()
    }.flow

    private suspend fun <T> getResponse(
        request: suspend () -> Response<T>
    ): T = try {
        val result = request.invoke()
        if (result.isSuccessful) {
            result.body() ?: throw Throwable(UNKNOWN_ERROR_MESSAGE)
        } else {
            throw Throwable(Throwable(result.message()))
        }
    } catch (error: UnknownHostException) {
        throw error
    } catch (error: ConnectException) {
        throw error
    } catch (error: HttpException) {
        throw error
    } catch (error: Throwable) {
        throw error
    }

    companion object {
        private const val UNKNOWN_ERROR_MESSAGE = "UNKNOWN_ERROR_MESSAGE"
    }
}
