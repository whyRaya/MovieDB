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
import com.whyraya.moviedb.ui.genre.MovieGenreScreen
import com.whyraya.moviedb.ui.movies.MovieDetailScreen
import com.whyraya.moviedb.ui.movies.MovieDetailViewModel
import com.whyraya.moviedb.ui.movies.MoviesScreen
import com.whyraya.moviedb.ui.movies.MoviesViewModel
import com.whyraya.moviedb.ui.movies.VideoScreen
import com.whyraya.moviedb.ui.navigation.GENRE_ID
import com.whyraya.moviedb.ui.navigation.MOVIES_ROUTE
import com.whyraya.moviedb.ui.navigation.MOVIE_ID
import com.whyraya.moviedb.ui.navigation.MOVIE_ROUTE
import com.whyraya.moviedb.ui.navigation.Screen
import com.whyraya.moviedb.ui.navigation.YOUTUBE_KEY
import com.whyraya.moviedb.ui.navigation.YOUTUBE_ROUTE

val LocalNavController = compositionLocalOf<NavHostController> { error("No nav controller") }
val LocalDarkTheme = compositionLocalOf { mutableStateOf(false) }

@Composable
fun MainContent() {
    val navController = LocalNavController.current
    NavHost(navController = navController, startDestination = Screen.GENRES.route) {
        composable(Screen.GENRES.route) { MovieGenreScreen() }

        navigation(startDestination = Screen.MOVIES.route, route = MOVIES_ROUTE) {
            navArgument(GENRE_ID) { type = NavType.IntType }

            fun NavBackStackEntry.genreId(): Int {
                return arguments?.getInt(GENRE_ID)!!
            }

            val moviesViewModel: @Composable (movieId: Int) -> MoviesViewModel = { hiltViewModel() }

            composable(route = Screen.MOVIES.route) {
                MoviesScreen(moviesViewModel(it.genreId()))
            }
        }

        navigation(startDestination = Screen.DETAIL.route, route = MOVIE_ROUTE) {
            navArgument(MOVIE_ID) { type = NavType.IntType }

            fun NavBackStackEntry.movieId(): Int {
                return arguments?.getInt(MOVIE_ID)!!
            }

            val movieDetailViewModel: @Composable (movieId: Int) -> MovieDetailViewModel =
                { hiltViewModel() }

            composable(route = Screen.DETAIL.route) {
                MovieDetailScreen(movieDetailViewModel(it.movieId()))
            }
        }

        navigation(startDestination = Screen.YOUTUBE.route, route = YOUTUBE_ROUTE) {
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
