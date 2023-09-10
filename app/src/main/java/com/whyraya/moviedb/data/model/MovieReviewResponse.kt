package com.whyraya.moviedb.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MovieReviewResponse(
    @SerializedName("author") val author: String? = null,
    @SerializedName("author_details") val authorDetails: AuthorDetails? = null,
    @SerializedName("content") val content: String? = null,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("id") val id: String? = null,
    @SerializedName("updated_at") val updatedAt: String? = null,
    @SerializedName("url") val url: String? = null
) {

    @Keep
    data class AuthorDetails(
        @SerializedName("avatar_path") val avatarPath: String? = null,
        @SerializedName("name") val name: String? = null,
        @SerializedName("rating") val rating: Double? = null,
        @SerializedName("username") val username: String? = null
    )
}
