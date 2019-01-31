package com.example.jll.hackernewsofflinefirst.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonQualifier
import java.io.Serializable

data class ArticleJsonResponse(
  val hits: List<Article>
) : Serializable

@Entity(tableName = "articles")
class Article(
  @PrimaryKey val objectID: String,
  val created_at: String,
  val title: String?,
  val story_title: String?,
  val author: String,
  val deleted: Boolean?

) : Serializable {
  fun getArticleTitle(): String {
    return if (title.isNullOrEmpty()) story_title!! else title!!
  }
}

