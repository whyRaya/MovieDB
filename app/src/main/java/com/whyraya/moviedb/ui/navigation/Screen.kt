package com.whyraya.moviedb.ui.navigation

const val MOVIE_ID = "MOVIE_ID"
const val YOUTUBE_KEY = "YOUTUBE_KEY"

enum class Screen(val route: String) {
    MOVIES("movies"),
    DETAIL("movie/{$MOVIE_ID}"),
    YOUTUBE("youtube/{$YOUTUBE_KEY}");

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
