package com.example.jll.hackernewsofflinefirst

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import com.example.jll.hackernewsofflinefirst.di.component.DaggerAppComponent
import com.example.jll.hackernewsofflinefirst.di.modules.AppModule
import com.example.jll.hackernewsofflinefirst.network.NetworkModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class HackerNewsOfflineFirstApp: Application(), HasActivityInjector, HasSupportFragmentInjector {

  @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>
  @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

  override fun onCreate() {
    super.onCreate()

    DaggerAppComponent.builder().appModule(AppModule(this)).networkModule(NetworkModule(BuildConfig.URL)).build().inject(this)
  }

  override fun activityInjector(): AndroidInjector<Activity> = activityInjector

  override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector
}