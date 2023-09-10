package com.whyraya.moviedb.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieGenreDto(
    val id: Int = 0,
    val name: String = ""
) : Parcelable
