package com.whyraya.moviedb.data.remote.paging

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BasePagingResponse<T>(
    @SerializedName("results") val results: T? = null,
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int,
)
