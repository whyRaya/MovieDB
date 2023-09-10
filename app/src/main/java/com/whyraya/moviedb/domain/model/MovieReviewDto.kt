package com.whyraya.moviedb.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieReviewDto(
    val author: String = "",
    val content: String = "",
    val createdAt: String = "",
    val id: String = "",
    val updatedAt: String = "",
    val url: String = "",
    val avatarPath: String = "",
    val name: String = "",
    val rating: Double = 0.0,
    val username: String = ""
) : Parcelable
