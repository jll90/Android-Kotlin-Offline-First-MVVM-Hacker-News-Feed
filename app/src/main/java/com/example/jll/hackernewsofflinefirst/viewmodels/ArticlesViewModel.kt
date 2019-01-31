package com.example.jll.hackernewsofflinefirst.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.jll.hackernewsofflinefirst.models.Article
import com.example.jll.hackernewsofflinefirst.repos.ArticleRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ArticlesViewModel @Inject constructor(
  private val articlesRepository: ArticleRepository
) : ViewModel() {

  var articlesResult: MutableLiveData<List<Article>> = MutableLiveData()
  var articlesError: MutableLiveData<String> = MutableLiveData()

  lateinit var indexObserver: DisposableObserver<List<Article>>
//  lateinit var deleteObserver: DisposableObserver<Int>

  fun articlesResult(): LiveData<List<Article>> {
    return articlesResult
  }

  fun articlesError(): LiveData<String> {
    return articlesError
  }

  fun fetchArticles() {

    indexObserver = object : DisposableObserver<List<Article>>() {
      override fun onComplete() {
      }

      override fun onNext(articles: List<Article>) {
        val filteredArticles = articles.distinctBy { a -> a.objectID }.filter { a -> a.deleted != true }
        articlesResult.postValue(filteredArticles)
      }

      override fun onError(e: Throwable) {
        articlesError.postValue(e.message)
      }
    }

    articlesRepository.getArticles()
      .subscribeOn(Schedulers.newThread())
      .observeOn(AndroidSchedulers.mainThread())
      .debounce(400, TimeUnit.MILLISECONDS)
      .subscribe(indexObserver)

  }

  fun deleteArticle(article: Article) {
    articlesRepository.markAsDeleted(article)
  }

  fun disposeElements() {
    if (!indexObserver.isDisposed) indexObserver.dispose()
  }
}