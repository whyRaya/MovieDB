package com.whyraya.moviedb.ui.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyraya.moviedb.domain.MovieRepository
import com.whyraya.moviedb.domain.model.MovieGenreDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieGenreViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieGenreUiState())
    val uiState: StateFlow<MovieGenreUiState> = _uiState.asStateFlow()

    init {
        getMovieGenres()
    }

    fun getMovieGenres() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(loading = true, error = null)
        try {
            movieRepository.getMovieGenres().collect {
                _uiState.value = _uiState.value.copy(
                    genre = it,
                    loading = false,
                )
            }
        } catch (exception: Exception) {
            _uiState.value = _uiState.value.copy(error = exception, loading = false)
        }
    }

    data class MovieGenreUiState(
        val genre: List<MovieGenreDto>? = null,
        val loading: Boolean = false,
        val error: Throwable? = null
    )
}
