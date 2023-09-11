package com.whyraya.moviedb.ui.movies

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.whyraya.moviedb.domain.MovieDto
import com.whyraya.moviedb.domain.MovieRepository
import com.whyraya.moviedb.ui.navigation.GENRE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    movieRepository: MovieRepository
) : ViewModel() {

    private val genreId = savedStateHandle.get<String>(GENRE_ID)!!.toInt()

    val movies: Flow<PagingData<MovieDto>> = movieRepository.getMovie(genreId)
}
