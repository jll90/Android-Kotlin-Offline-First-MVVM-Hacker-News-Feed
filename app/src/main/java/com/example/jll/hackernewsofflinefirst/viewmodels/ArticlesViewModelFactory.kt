package com.example.jll.hackernewsofflinefirst.viewmodels

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

class ArticlesViewModelFactory @Inject constructor(
  private val articlesVM: ArticlesViewModel) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(ArticlesViewModel::class.java!!)) {
      return articlesVM as T
    }
    throw IllegalArgumentException("Unknown class name")
  }
}