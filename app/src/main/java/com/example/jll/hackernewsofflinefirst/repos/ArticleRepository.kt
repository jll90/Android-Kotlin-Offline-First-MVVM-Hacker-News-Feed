package com.example.jll.hackernewsofflinefirst.repos

import android.support.annotation.WorkerThread
import com.example.jll.hackernewsofflinefirst.dao.ArticlesDao
import com.example.jll.hackernewsofflinefirst.models.Article
import com.example.jll.hackernewsofflinefirst.network.ArticlesApi
import io.reactivex.Observable
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import javax.inject.Inject


class ArticleRepository @Inject constructor(private val articlesApi: ArticlesApi,
                                            private val articlesDao: ArticlesDao) {

  suspend fun getArticles(): Observable<List<Article>> {

    val apiObservable = getArticlesFromApi()
    val dbObservable = getArticlesFromDb()

    return if (hasInternetConnection()) {
      Observable.concatArrayEager(apiObservable, dbObservable)
    } else {
      dbObservable
    }

  }

  @WorkerThread
  private suspend fun hasInternetConnection(): Boolean {
    return try {
      val timeoutMs = 800
      val socket = Socket()
      val socketAddress = InetSocketAddress("8.8.8.8", 53)

      socket.connect(socketAddress, timeoutMs)
      socket.close()
      true
    } catch (e: IOException) {
      false
    }
  }

  @WorkerThread
  suspend fun markAsDeleted(article: Article) {
    articlesDao.markAsDeleted(article.objectID)
  }

  private fun getArticlesFromApi(): Observable<List<Article>> {
    return articlesApi.getArticles()
      .map { res ->
        res.hits
      }
      .doOnNext {
        val articles = it
        for (article in articles) {
          articlesDao.insertArticle(article)
        }
      }
  }

  private fun getArticlesFromDb(): Observable<List<Article>> {
    return articlesDao.queryArticles().toObservable()
  }
}
