package com.example.jll.hackernewsofflinefirst.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.jll.hackernewsofflinefirst.models.Article
import com.example.jll.hackernewsofflinefirst.repos.ArticleRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.experimental.CoroutineContext

class ArticlesViewModel @Inject constructor(
  private val articlesRepository: ArticleRepository
) : ViewModel() {

  private val articlesResult: MutableLiveData<List<Article>> = MutableLiveData()
  private val articlesError: MutableLiveData<String> = MutableLiveData()
  private val refreshFinishes: MutableLiveData<Boolean> = MutableLiveData()
  private val rvScroll: MutableLiveData<Int> = MutableLiveData()
  private val showNoData: MediatorLiveData<Boolean> = MediatorLiveData()
  private val repoLoading: MutableLiveData<Boolean> = MutableLiveData()
  private lateinit var indexObserver: DisposableObserver<List<Article>>

  init {
    articlesResult.value = listOf()
    rvScroll.value = 0
    repoLoading.value = false

    showNoData.addSource(articlesResult, {
      showNoData.value = !repoLoading.value!! && it!!.count() == 0
    })

    showNoData.addSource(repoLoading, {
      showNoData.value = !it!! && articlesResult.value!!.count() == 0
    })
  }

  private var parentJob = Job()
  private val coroutineContext: CoroutineContext
    get() = parentJob + Dispatchers.Main

  private val scope = CoroutineScope(coroutineContext)

  fun getRvScroll(): Int {
    return rvScroll.value!!
  }

  fun showNoData(): LiveData<Boolean> {
    return showNoData
  }

  fun setRvScroll(scroll: Int) {
    rvScroll.value = scroll
  }

  fun articlesResult(): LiveData<List<Article>> {
    return articlesResult
  }

  fun articlesError(): LiveData<String> {
    return articlesError
  }

  fun refreshFinishes(): LiveData<Boolean> {
    return refreshFinishes
  }


  fun fetchArticles() = scope.launch(Dispatchers.IO) {

    repoLoading.postValue(true)

    indexObserver = object : DisposableObserver<List<Article>>() {
      override fun onComplete() {
        refreshFinishes.value = false
        repoLoading.value = false
      }

      override fun onNext(articles: List<Article>) {
        val filteredArticles = articles
          .distinctBy { a -> a.objectID }
          .filter { a -> a.deleted != true }
          .sortedWith(compareBy { it.created_at })
          .reversed()
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

  fun deleteArticle(article: Article) = scope.launch(Dispatchers.IO) {
    articlesRepository.markAsDeleted(article)
    val articlesMinus = articlesResult.value!!.filter { a ->
      article.objectID !== a.objectID
    }
    articlesResult.postValue(articlesMinus)
  }

  fun disposeElements() {
    if (!indexObserver.isDisposed) indexObserver.dispose()
  }
}