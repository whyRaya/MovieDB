package com.whyraya.moviedb.ui.navigation

const val GENRES_ROUTE = "genres"

const val MOVIES_ROUTE = "movies"
const val GENRE_ID = "GENRE_ID"

const val MOVIE_ROUTE = "movie"
const val MOVIE_ID = "MOVIE_ID"

const val YOUTUBE_ROUTE = "youtube"
const val YOUTUBE_KEY = "YOUTUBE_KEY"

enum class Screen(val route: String) {
    GENRES(GENRES_ROUTE),
    MOVIES("$MOVIES_ROUTE/{$GENRE_ID}"),
    DETAIL("$MOVIE_ROUTE/{$MOVIE_ID}"),
    YOUTUBE("$YOUTUBE_ROUTE/{$YOUTUBE_KEY}");

    fun createPath(vararg args: Any): String {
        var route = route
        require(args.size == route.argumentCount) {
            "Provided ${args.count()} parameters, was expected ${route.argumentCount} parameters!"
        }
        route.arguments().forEachIndexed { index, matchResult ->
            route = route.replace(matchResult.value, args[index].toString())
        }
        return route
    }
}
