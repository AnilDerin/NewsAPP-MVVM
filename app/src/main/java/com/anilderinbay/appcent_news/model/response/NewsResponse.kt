package com.anilderinbay.appcent_news.model.response


import android.os.Parcelable
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


class NewsResponse {
    @SerializedName("articles")
    var articles: ArrayList<Article>? = null

    @SerializedName("status")
    var status: String? = null

    @SerializedName("totalResults")
    var totalResults: Int? = null




    @Parcelize
    data class Article(
        @SerializedName("author")
        var author: String? = null,
        @SerializedName("content")
        var content: String? = null,
        @SerializedName("description")
        var description: String? = null,
        @SerializedName("publishedAt")
        var publishedAt: String? = null,
        @SerializedName("source")
        var source: Source? = null,
        @SerializedName("title")
        var title: String? = null,
        @SerializedName("url")
        var url: String? = null,
        @SerializedName("urlToImage")
        var urlToImage: String? = null
    ): Parcelable
    {

        @Parcelize
        data class Source(
            @SerializedName("id")
            var id: String? = null,
            @SerializedName("name")
            var name: String? = null
        ):Parcelable
    }
}