package com.whyraya.moviedb.di

import com.whyraya.moviedb.domain.MovieRepository
import com.whyraya.moviedb.domain.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideMovieRepositoryImpl(repository: MovieRepositoryImpl): MovieRepository
}


