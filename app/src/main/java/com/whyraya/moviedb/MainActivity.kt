package com.whyraya.moviedb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.whyraya.moviedb.ui.LocalDarkTheme
import com.whyraya.moviedb.ui.LocalNavController
import com.whyraya.moviedb.ui.MainContent
import com.whyraya.moviedb.ui.theme.MovieTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            val systemTheme = isSystemInDarkTheme()
            val isDarkTheme = remember { mutableStateOf(systemTheme) }
            val navController = rememberNavController()
            MovieTheme(isDarkTheme = isDarkTheme.value) {
                CompositionLocalProvider(
                    LocalNavController provides navController,
                    LocalDarkTheme provides isDarkTheme,
                ) {
                    MainContent()
                }
            }
        }
    }
}
