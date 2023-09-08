package com.whyraya.moviedb.ui.movies

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.whyraya.moviedb.domain.MovieDto
import com.whyraya.moviedb.domain.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    val movies: Flow<PagingData<MovieDto>> = movieRepository.getMovie()
}
