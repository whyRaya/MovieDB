package com.whyraya.moviedb.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieVideosDto(
    val id: String = "",
    val iso31661: String = "",
    val iso6391: String = "",
    val key: String = "",
    val name: String = "",
    val official: Boolean = false,
    val publishedAt: String = "",
    val site: String = "",
    val size: Int = 0,
    val type: String = "",
    val thumbnail: String = "",
) : Parcelable
