package com.whyraya.moviedb.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.whyraya.moviedb.domain.MovieRepository
import com.whyraya.moviedb.domain.model.MovieDetailDto
import com.whyraya.moviedb.domain.model.MovieReviewDto
import com.whyraya.moviedb.ui.navigation.MOVIE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val movieId = savedStateHandle.get<String>(MOVIE_ID)!!.toInt()

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    val movieReview: Flow<PagingData<MovieReviewDto>> = movieRepository.getMovieReview(movieId)

    init {
        getMovieDetail()
    }

    fun getMovieDetail() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(loading = true, error = null)
        try {
            movieRepository.getMovieDetail(movieId).collect {
                _uiState.value = _uiState.value.copy(
                    movieDetail = it,
                    loading = false,
                )
            }
        } catch (exception: Exception) {
            _uiState.value = _uiState.value.copy(error = exception, loading = false)
        }
    }

    data class MovieDetailUiState(
        val movieDetail: MovieDetailDto? = null,
        val loading: Boolean = false,
        val error: Throwable? = null
    )
}
