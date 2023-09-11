package com.whyraya.moviedb.ui.genre


import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whyraya.moviedb.R
import com.whyraya.moviedb.domain.model.MovieGenreDto
import com.whyraya.moviedb.ui.LocalDarkTheme
import com.whyraya.moviedb.ui.LocalNavController
import com.whyraya.moviedb.ui.movies.ErrorColumn
import com.whyraya.moviedb.ui.movies.LoadingColumn
import com.whyraya.moviedb.ui.movies.MovieAppBar
import com.whyraya.moviedb.ui.navigation.Screen

val LocalVibrantColor =
    compositionLocalOf<Animatable<Color, AnimationVector4D>> { error("No vibrant color defined") }

@Composable
fun MovieGenreScreen() {
    val genreViewModel = hiltViewModel<MovieGenreViewModel>()
    val uiState = genreViewModel.uiState.collectAsState().value
    when {
        uiState.loading -> LoadingColumn(stringResource(id = R.string.app_loading))
        uiState.error != null -> ErrorColumn(uiState.error.message.orEmpty()) {
            genreViewModel.getMovieGenres()
        }
        uiState.genre != null -> {
            val defaultTextColor = colors.onBackground
            val vibrantColor = remember { Animatable(defaultTextColor) }
            CompositionLocalProvider(
                LocalVibrantColor provides vibrantColor,
            ) {
                MovieGenre(uiState.genre)
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun MovieGenre(genres: List<MovieGenreDto>) {
    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .background(colors.surface),
        topBar = {
            Surface(modifier = Modifier.fillMaxWidth(), elevation = 16.dp) {
                Column(
                    Modifier
                        .background(colors.surface)
                        .padding(bottom = 2.dp),
                ) {
                    MovieAppBar()
                }
            }
        },
        content = {
            GenreList(genres = genres)
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GenreList(genres: List<MovieGenreDto>) {
    val navController = LocalNavController.current
    val shape = RoundedCornerShape(percent = 50)
    val isDarkTheme = LocalDarkTheme.current
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.app_select_genres),
            color = if (isDarkTheme.value) Color.Yellow else Color.Black,
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            ),
        )
        Spacer(modifier = Modifier.size(21.dp))
        FlowRow(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            genres.forEach { genre ->
                val colors = listOf(getRandomColor(), getRandomColor())
                Text(
                    text = genre.name,
                    color = if (isDarkTheme.value) Color.Yellow else Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.body2.copy(
                        fontSize = 17.sp,
                        fontFamily = FontFamily.SansSerif
                    ),
                    modifier = Modifier
                        .shadow(animateDpAsState(4.dp).value, RoundedCornerShape(percent = 50))
                        .background(MaterialTheme.colors.surface)
                        .border(2.dp, Brush.horizontalGradient(colors), shape)
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                        .clickable {
                            navController.navigate(Screen.MOVIES.createPath(genre.id))
                        }

                )
            }
        }
    }
}

fun getRandomColor(): Color {
    val colorRange = 0..256
    return Color(colorRange.random(), colorRange.random(), colorRange.random())
}
