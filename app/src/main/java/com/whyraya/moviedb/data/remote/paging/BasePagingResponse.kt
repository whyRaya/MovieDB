package com.whyraya.moviedb.data.remote.paging

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BasePagingResponse<T>(
    @SerializedName("results") val results: T? = null,
    @SerializedName("page") val page: Int? = null,
    @SerializedName("total_pages") val totalPages: Int? = null,
    @SerializedName("total_results") val totalResults: Int? = null,
)
