package com.example.jll.hackernewsofflinefirst.di.modules

import com.example.jll.hackernewsofflinefirst.IndexFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
  @ContributesAndroidInjector
  internal abstract fun contributeIndexFragment(): IndexFragment
}