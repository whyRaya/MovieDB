package com.whyraya.moviedb.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.whyraya.moviedb.ui.movies.MovieDetailScreen
import com.whyraya.moviedb.ui.movies.MovieDetailViewModel
import com.whyraya.moviedb.ui.movies.MoviesListScreen
import com.whyraya.moviedb.ui.movies.VideoScreen
import com.whyraya.moviedb.ui.navigation.MOVIE_ID
import com.whyraya.moviedb.ui.navigation.Screen
import com.whyraya.moviedb.ui.navigation.YOUTUBE_KEY

val LocalNavController = compositionLocalOf<NavHostController> { error("No nav controller") }
val LocalDarkTheme = compositionLocalOf { mutableStateOf(false) }

@Composable
fun MainContent() {
    val navController = LocalNavController.current
    NavHost(navController = navController, startDestination = Screen.MOVIES.route) {
        composable(Screen.MOVIES.route) { MoviesListScreen() }

        navigation(startDestination = Screen.DETAIL.route, route = "movie") {
            navArgument(MOVIE_ID) { type = NavType.IntType }

            fun NavBackStackEntry.movieId(): Int {
                return arguments?.getInt(MOVIE_ID)!!
            }

            val movieDetailViewModel: @Composable (movieId: Int) -> MovieDetailViewModel = { hiltViewModel() }

            composable(route = Screen.DETAIL.route) {
                MovieDetailScreen(movieDetailViewModel(it.movieId()))
            }
        }

        navigation(startDestination = Screen.YOUTUBE.route, route = "youtube") {
            navArgument(YOUTUBE_KEY) { type = NavType.StringType }

            fun NavBackStackEntry.youtubeUrl(): String {
                return arguments?.getString(YOUTUBE_KEY)!!
            }
            composable(route = Screen.YOUTUBE.route) {
                VideoScreen(it.youtubeUrl())
            }
        }
    }
}
