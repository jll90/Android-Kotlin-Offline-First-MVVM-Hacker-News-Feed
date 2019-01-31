package com.example.jll.hackernewsofflinefirst.network

import com.example.jll.hackernewsofflinefirst.models.ArticleJsonResponse
import io.reactivex.Observable
import retrofit2.http.GET


interface ArticlesApi {
  @GET("search_by_date?query=android")
  fun getArticles(): Observable<ArticleJsonResponse>
}

