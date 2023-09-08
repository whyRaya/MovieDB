package com.whyraya.moviedb.ui.movies


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.whyraya.moviedb.domain.MovieDto
import com.whyraya.moviedb.R

private const val COLUMN_COUNT = 2
private val GRID_SPACING = 8.dp

private val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(COLUMN_COUNT) }

@Composable
fun MoviesListScreen() {
    val moviesViewModel = hiltViewModel<MoviesViewModel>()
    val movies = moviesViewModel.movies.collectAsLazyPagingItems()
    val state = rememberLazyGridState()

    when (movies.loadState.refresh) {
        is LoadState.Loading -> {
            LoadingColumn("Loading...")
        }
        is LoadState.Error -> {
            val error = movies.loadState.refresh as LoadState.Error
            ErrorColumn("Error: ${error.error.message.orEmpty()}")
        }
        else -> {
            LazyMoviesGrid(state, movies)
        }
    }
}

@Composable
private fun LazyMoviesGrid(state: LazyGridState, moviePagingItems: LazyPagingItems<MovieDto>) {
    val onMovieClicked: (Int) -> Unit = {  }
    LazyVerticalGrid(
        columns = GridCells.Fixed(COLUMN_COUNT),
        contentPadding = PaddingValues(
            start = GRID_SPACING,
            end = GRID_SPACING,
            bottom = WindowInsets.navigationBars.getBottom(LocalDensity.current).toDp().dp.plus(GRID_SPACING),
        ),
        horizontalArrangement = Arrangement.spacedBy(GRID_SPACING, Alignment.CenterHorizontally),
        state = state,
        content = {
            if (moviePagingItems.itemCount == 0 && moviePagingItems.loadState.refresh !is LoadState.Loading) {
                item(span = span) {
                    ErrorRow("Error")
                }
            }
            items(moviePagingItems.itemCount) { index ->
                val movie = moviePagingItems.peek(index) ?: return@items
                MovieContent(
                    movie,
                    Modifier
                        .height(320.dp)
                        .padding(vertical = GRID_SPACING),
                    onMovieClicked,
                )
            }
            renderLoading(moviePagingItems.loadState)
            renderError(moviePagingItems.loadState)
        },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieContent(movie: MovieDto, modifier: Modifier = Modifier, onMovieClicked: (Int) -> Unit = {}) {
    Box(modifier = modifier) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = 12.dp),
            shape = RoundedCornerShape(size = 8.dp),
            elevation = 8.dp,
            onClick = { onMovieClicked(movie.id) },
        ) {
            Box {
                MovieName(name = movie.name)
            }
        }
    }
}


@Composable
private fun MovieName(name: String) = Text(
    text = name,
    style = MaterialTheme.typography.subtitle1.copy(
        color = Color.Black,
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.W500,
        letterSpacing = 1.5.sp,
    ),
    maxLines = 1,
    overflow = TextOverflow.Ellipsis,
)

private fun LazyGridScope.renderLoading(loadState: CombinedLoadStates) {
    if (loadState.append !is LoadState.Loading) return

    item(span = span) {
        val title = stringResource(R.string.get_more_movies)
        LoadingRow(title = title, modifier = Modifier.padding(vertical = GRID_SPACING))
    }
}

private fun LazyGridScope.renderError(loadState: CombinedLoadStates) {
    val message = (loadState.append as? LoadState.Error)?.error?.message ?: return

    item(span = span) {
        ErrorRow(title = message, modifier = Modifier.padding(vertical = GRID_SPACING))
    }
}

@Composable
fun LoadingColumn(title: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(title)
        CircularProgressIndicator(modifier = Modifier
            .size(40.dp)
            .padding(top = 16.dp))
    }
}

@Composable
fun LoadingRow(title: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircularProgressIndicator(modifier = Modifier.size(40.dp))
        Text(title)
    }
}

@Composable
fun ErrorColumn(message: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(message)
        Icon(
            imageVector = Icons.Filled.Face,
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .padding(top = 16.dp),
        )
    }
}

@Composable
fun ErrorRow(title: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Filled.ErrorOutline,
            contentDescription = "",
            modifier = Modifier.size(40.dp),
        )
        Text(title)
    }
}

@Composable
fun Int.toDp(): Float {
    val density = LocalDensity.current.density
    return remember(this) { this / density }
}
