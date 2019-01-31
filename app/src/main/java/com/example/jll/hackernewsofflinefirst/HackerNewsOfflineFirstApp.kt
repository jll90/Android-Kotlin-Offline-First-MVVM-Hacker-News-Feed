package com.example.jll.hackernewsofflinefirst

import android.app.Activity
import android.app.Application
import com.example.jll.hackernewsofflinefirst.di.component.DaggerAppComponent
import com.example.jll.hackernewsofflinefirst.di.modules.AppModule
import com.example.jll.hackernewsofflinefirst.network.NetworkModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class HackerNewsOfflineFirstApp: Application(), HasActivityInjector {

  @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>

  override fun onCreate() {
    super.onCreate()

    DaggerAppComponent.builder().appModule(AppModule(this)).networkModule(NetworkModule(BuildConfig.URL)).build().inject(this)
  }

  override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}