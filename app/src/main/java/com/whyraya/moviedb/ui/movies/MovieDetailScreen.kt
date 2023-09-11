package com.whyraya.moviedb.ui.movies

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.whyraya.moviedb.R
import com.whyraya.moviedb.domain.model.MovieDetailDto
import com.whyraya.moviedb.domain.model.MovieGenreDto
import com.whyraya.moviedb.domain.model.MovieReviewDto
import com.whyraya.moviedb.domain.model.MovieVideosDto
import com.whyraya.moviedb.ui.LocalNavController
import com.whyraya.moviedb.ui.navigation.Screen

val LocalVibrantColor =
    compositionLocalOf<Animatable<Color, AnimationVector4D>> { error("No vibrant color defined") }
val LocalMovieId = compositionLocalOf<Int> { error("No movieId defined") }

@Composable
fun MovieDetailScreen(movieDetailViewModel: MovieDetailViewModel) {
    val uiState = movieDetailViewModel.uiState.collectAsState().value
    when {
        uiState.loading -> LoadingColumn(stringResource(id = R.string.app_get_movies_detail))
        uiState.error != null -> ErrorColumn(uiState.error.message.orEmpty()) {
            movieDetailViewModel.getMovieDetail()
        }

        uiState.movieDetail != null -> {
            val defaultTextColor = MaterialTheme.colors.onBackground
            val vibrantColor = remember { Animatable(defaultTextColor) }
            CompositionLocalProvider(
                LocalVibrantColor provides vibrantColor,
                LocalMovieId provides uiState.movieDetail.id,
            ) {
                MovieDetail(movieDetailViewModel, uiState.movieDetail)

            }
        }
    }
}

@Composable
fun MovieDetail(movieDetailViewModel: MovieDetailViewModel, movieDetail: MovieDetailDto) {
    val reviewsPaging = movieDetailViewModel.movieReview.collectAsLazyPagingItems()
    LazyColumn(
        contentPadding = PaddingValues(
            bottom = WindowInsets.navigationBars.getBottom(LocalDensity.current)
                .toDp().dp.plus(16.dp),
        )
    ) {
        item {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.surface)
            ) {
                val (backdrop, poster, title, genres, info, overview, videos) = createRefs()
                val startGuideline = createGuidelineFromStart(16.dp)
                val endGuideline = createGuidelineFromEnd(16.dp)
                MovieBackdrop(
                    backdropUrl = movieDetail.backdropPath,
                    Modifier.constrainAs(backdrop) {})
                MoviePoster(
                    movieDetail.posterPath,
                    Modifier
                        .zIndex(17f)
                        .width(180.dp)
                        .height(240.dp)
                        .constrainAs(poster) {
                            centerAround(backdrop.bottom)
                            linkTo(startGuideline, endGuideline)
                        },
                )
                MovieTitle(
                    title = movieDetail.title,
                    originalTitle = movieDetail.originalTitle,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .constrainAs(title) {
                            top.linkTo(poster.bottom, 8.dp)
                            linkTo(startGuideline, endGuideline)
                        }
                )
                MovieInfo(
                    movieDetail = movieDetail,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .constrainAs(info) {
                            top.linkTo(title.bottom, 16.dp)
                            linkTo(startGuideline, endGuideline)
                        }
                )
                MovieGenre(
                    genres = movieDetail.genres,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .constrainAs(genres) {
                            top.linkTo(info.bottom, 16.dp)
                            linkTo(startGuideline, endGuideline)
                        }
                )
                MovieOverview(overview = movieDetail.overview, modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .constrainAs(overview) {
                        top.linkTo(genres.bottom, 32.dp)
                        linkTo(startGuideline, endGuideline)
                    }
                )
                MovieVideo(
                    videos = movieDetail.videos,
                    modifier = Modifier.constrainAs(videos) {
                        top.linkTo(overview.bottom, 21.dp)
                        linkTo(startGuideline, endGuideline)
                    }
                )
            }
        }
        item {
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 21.dp, bottom = 8.dp),
                text = stringResource(R.string.app_review),
                color = LocalVibrantColor.current.value,
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                ),
            )
        }
        items(reviewsPaging.itemCount) { index ->
            val review = reviewsPaging.peek(index) ?: return@items
            MovieReview(review)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MovieBackdrop(backdropUrl: String, modifier: Modifier) {
    Card(
        elevation = 16.dp,
        shape = BottomArcShape(arcHeight = 160.dpToPx()),
        backgroundColor = LocalVibrantColor.current.value.copy(alpha = 0.1f),
        modifier = modifier.height(320.dp),
    ) {
        GlideImage(
            model = backdropUrl,
            contentDescription = backdropUrl,
            modifier = modifier.fillMaxWidth(),
            contentScale = ContentScale.FillHeight
        )
    }
}

val springAnimation = spring(
    dampingRatio = Spring.DampingRatioMediumBouncy,
    stiffness = Spring.StiffnessLow,
    visibilityThreshold = 0.001f,
)

@OptIn(ExperimentalMaterialApi::class, ExperimentalGlideComposeApi::class)
@Composable
private fun MoviePoster(posterUrl: String, modifier: Modifier) {
    val isScaled = remember { mutableStateOf(false) }
    val scale = animateFloatAsState(
        targetValue = if (isScaled.value) 2.2f else 1f,
        animationSpec = springAnimation,
        label = "scale"
    ).value

    Card(
        elevation = 24.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.scale(scale),
        onClick = { isScaled.value = !isScaled.value },
    ) {
        GlideImage(
            model = posterUrl,
            contentDescription = posterUrl,
            contentScale = ContentScale.FillBounds,
        )
    }
}

@Composable
private fun MovieTitle(title: String, originalTitle: String, modifier: Modifier) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title,
            style = MaterialTheme.typography.body1.copy(
                fontSize = 26.sp,
                letterSpacing = 3.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.SansSerif,
            ),
        )
        if (originalTitle.isNotBlank() && title != originalTitle) {
            Text(
                text = "(${originalTitle})",
                style = MaterialTheme.typography.subtitle2.copy(
                    fontStyle = FontStyle.Italic,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.SansSerif
                ),
            )
        }
    }
}

@Composable
private fun MovieInfo(movieDetail: MovieDetailDto, modifier: Modifier) {
    val spaceBetweenItem = 21.dp
    val spaceWithinItem = 6.dp
    val iconSize = 18.dp
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.CalendarToday,
            contentDescription = null,
            tint = Color.DarkGray,
            modifier = Modifier.size(iconSize)
        )
        Spacer(modifier = Modifier.size(spaceWithinItem))
        Text(
            text = movieDetail.releaseDate,
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif
            ),
        )
        Spacer(modifier = Modifier.size(spaceBetweenItem))
        Icon(
            imageVector = Icons.Default.Timer,
            contentDescription = null,
            tint = Color.DarkGray,
            modifier = Modifier.size(iconSize)
        )
        Spacer(modifier = Modifier.size(spaceWithinItem))
        Text(
            text = "${movieDetail.runtime} min",
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif
            ),
        )
        Spacer(modifier = Modifier.size(spaceBetweenItem))
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = Color.DarkGray,
            modifier = Modifier.size(iconSize)
        )
        Spacer(modifier = Modifier.size(spaceWithinItem))
        Text(
            text = "${movieDetail.voteAverage}/10",
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif
            ),
        )
        Spacer(modifier = Modifier.size(spaceWithinItem))
        Text(
            text = "(${movieDetail.voteCount})",
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif
            ),
        )
    }
}

@Composable
private fun MovieGenre(genres: List<MovieGenreDto>, modifier: Modifier) {
    Row(
        modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
    ) {
        genres.map { it.name }.forEachIndexed { index, name ->
            Text(
                text = name,
                style = MaterialTheme.typography.subtitle1.copy(letterSpacing = 2.sp),
                modifier = Modifier
                    .border(1.25.dp, LocalVibrantColor.current.value, RoundedCornerShape(50))
                    .padding(horizontal = 8.dp, vertical = 6.dp),
            )
            if (index != genres.lastIndex) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
private fun MovieOverview(overview: String, modifier: Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = stringResource(R.string.app_overview),
            color = LocalVibrantColor.current.value,
            style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            ),
        )
        Text(
            text = overview,
            style = MaterialTheme.typography.body2.copy(
                letterSpacing = 2.sp,
                lineHeight = 25.sp,
                fontFamily = FontFamily.SansSerif,
            )
        )
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun MovieVideo(
    videos: List<MovieVideosDto>,
    modifier: Modifier,
) {
    val navController = LocalNavController.current
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
            text = stringResource(R.string.app_videos),
            color = LocalVibrantColor.current.value,
            style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            ),
        )
        LazyRow(
            modifier = Modifier.testTag(LocalContext.current.getString(R.string.app_videos)),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
            content = {
                items(videos.size) { index ->
                    val video = videos[index]
                    Card(
                        Modifier
                            .width(180.dp)
                            .height(118.dp)
                            .padding(end = 8.dp)
                            .clickable {
                                navController.navigate(Screen.YOUTUBE.createPath(video.key))
                            },
                        shape = RoundedCornerShape(8.dp),
                        elevation = 8.dp,
                    ) {
                        GlideImage(
                            model = video.thumbnail,
                            contentDescription = video.thumbnail,
                            contentScale = ContentScale.FillBounds,
                        )
                    }
                }

            }
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MovieReview(
    review: MovieReviewDto
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        var showMore by remember { mutableStateOf(false) }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Card(shape = CircleShape, elevation = 8.dp, modifier = Modifier.size(32.dp)) {
                GlideImage(
                    model = review.avatarPath,
                    contentDescription = review.avatarPath,
                    contentScale = ContentScale.FillBounds,
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = review.author,
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.SansSerif,
                ),
            )
            Spacer(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
            Text(
                text = review.updatedAt,
                style = MaterialTheme.typography.subtitle1.copy(
                    fontFamily = FontFamily.SansSerif,
                ),
            )
        }
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color.Yellow,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                text = "${review.rating}/10",
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.SansSerif,
                ),
            )
        }
        if (showMore) {
            Text(
                text = review.content,
                style = MaterialTheme.typography.body2.copy(
                    letterSpacing = 1.sp,
                    lineHeight = 21.sp,
                    fontFamily = FontFamily.SansSerif,
                ),
                modifier = Modifier.padding(top = 8.dp)
            )
        } else {
            Text(
                text = review.content,
                style = MaterialTheme.typography.body2.copy(
                    letterSpacing = 1.sp,
                    lineHeight = 21.sp,
                    fontFamily = FontFamily.SansSerif,
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        Text(
            text = stringResource(if (showMore) R.string.app_see_less else R.string.app_see_more),
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.SemiBold),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = Color.Blue,
            modifier = Modifier
                .padding(top = 8.dp)
                .clickable {
                    showMore = !showMore
                }
        )

    }

}


