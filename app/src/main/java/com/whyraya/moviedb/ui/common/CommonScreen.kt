package com.whyraya.moviedb.ui.common


import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.whyraya.moviedb.R
import com.whyraya.moviedb.ui.LocalDarkTheme
import com.whyraya.moviedb.ui.LocalNavController

class BottomArcShape(private val arcHeight: Float) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val path = Path().apply {
            moveTo(size.width, 0f)
            lineTo(size.width, size.height)
            val arcOffset = arcHeight / 10
            val rect = Rect(
                left = 0f - arcOffset,
                top = size.height - arcHeight,
                right = size.width + arcOffset,
                bottom = size.height,
            )
            arcTo(rect, 0f, 180f, false)
            lineTo(0f, 0f)
            close()
        }
        return Outline.Generic(path)
    }
}

@Composable
fun LoadingColumn(title: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colors.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(title)
        CircularProgressIndicator(
            modifier = Modifier
                .size(40.dp)
                .padding(top = 16.dp)
        )
    }
}

@Composable
fun LoadingRow(title: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircularProgressIndicator(modifier = Modifier.size(40.dp))
        Text(title)
    }
}

@Composable
fun ErrorColumn(message: String, modifier: Modifier = Modifier, reload: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colors.surface),
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
        Button(
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Red),
            onClick = { reload.invoke() }) {
            Text("Reload")
        }
    }
}

@Composable
fun ErrorRow(title: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
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
fun MovieAppBar() {
    val navController = LocalNavController.current
    val colors = MaterialTheme.colors
    val isDarkTheme = LocalDarkTheme.current
    val iconTint =
        animateColorAsState(
            if (isDarkTheme.value) colors.onSurface else colors.primary,
            label = "appIconTint"
        ).value
    Row(
        Modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconButton(
            onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "", tint = iconTint)
        }
        Text(text = stringResource(id = R.string.app_name))
        val icon = if (isDarkTheme.value) Icons.Default.NightsStay else Icons.Default.WbSunny
        IconButton(onClick = { isDarkTheme.value = !isDarkTheme.value }) {
            val desc = if (isDarkTheme.value) R.string.app_light_theme else R.string.app_dark_theme
            Icon(icon, contentDescription = stringResource(id = desc), tint = iconTint)
        }
    }
}

@Composable
fun Int.toDp(): Float {
    val density = LocalDensity.current.density
    return remember(this) { this / density }
}

@Composable
fun Int.dpToPx(): Float {
    val density = LocalDensity.current.density
    return remember(this) { this * density }
}
