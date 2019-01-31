package com.example.jll.hackernewsofflinefirst.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.jll.hackernewsofflinefirst.models.Article
import io.reactivex.Single

@Dao
interface ArticlesDao {
  @Query("SELECT * FROM articles")
  fun queryArticles(): Single<List<Article>>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  fun insertArticle(article: Article)

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  fun insertAllArticles(articles: List<Article>)

   @Query("UPDATE articles SET deleted = 1 WHERE objectID = :objectID")
  fun markAsDeleted(objectID: String)

}