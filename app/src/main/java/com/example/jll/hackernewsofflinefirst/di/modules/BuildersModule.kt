package com.example.jll.hackernewsofflinefirst.di.modules

import com.example.jll.hackernewsofflinefirst.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module

abstract class BuildersModule {
  @ContributesAndroidInjector

  abstract fun contributeMainActivity(): MainActivity
}