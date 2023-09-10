package com.whyraya.moviedb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import com.whyraya.moviedb.ui.movies.MovieDetailScreen
import com.whyraya.moviedb.ui.movies.MoviesListScreen
import com.whyraya.moviedb.ui.theme.MovieTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemTheme = isSystemInDarkTheme()
            val isDarkTheme = remember { mutableStateOf(systemTheme) }
            MovieTheme(isDarkTheme = isDarkTheme.value) {
//                MoviesListScreen()
                MovieDetailScreen()
            }
        }
    }
}
