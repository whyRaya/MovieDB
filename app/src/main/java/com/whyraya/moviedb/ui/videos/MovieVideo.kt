package com.whyraya.moviedb.ui.videos


import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun VideoScreen(videoKey: String) {
    val youtubeUrl = "https://www.youtube.com/watch?v=$videoKey"
    AndroidView(
        modifier = Modifier.background(MaterialTheme.colors.surface),
        factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            settings.apply {
                javaScriptEnabled = true
                loadsImagesAutomatically = true
                loadWithOverviewMode = true
                domStorageEnabled = true
                allowFileAccess = true
            }
            loadUrl(youtubeUrl)
        }
    }, update = {
        it.loadUrl(youtubeUrl)
    })
}
