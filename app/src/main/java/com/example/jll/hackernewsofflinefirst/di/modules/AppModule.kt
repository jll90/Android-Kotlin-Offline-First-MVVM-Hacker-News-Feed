package com.example.jll.hackernewsofflinefirst.di.modules

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import com.example.jll.hackernewsofflinefirst.dao.ArticlesDao
import com.example.jll.hackernewsofflinefirst.dao.Database
import com.example.jll.hackernewsofflinefirst.viewmodels.ArticlesViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: Application){
  @Provides
  @Singleton
  fun provideApplication(): Application = app

  @Provides
  @Singleton
  fun provideArticlesDatabase(app: Application): Database =
    Room.databaseBuilder(app, Database::class.java, "articles_db")
      .fallbackToDestructiveMigration()
      .build()

  @Provides
  @Singleton
  fun provideArticlesDao(database: Database): ArticlesDao = database.articlesDao()

  @Provides
  fun providesArticlesViewModelFactory(factory: ArticlesViewModelFactory): ViewModelProvider.Factory = factory
}