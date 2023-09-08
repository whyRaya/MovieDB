package com.whyraya.moviedb.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovie(): Flow<PagingData<MovieDto>>
}
