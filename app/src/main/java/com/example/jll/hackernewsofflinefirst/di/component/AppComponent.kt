package com.example.jll.hackernewsofflinefirst.di.component

import com.example.jll.hackernewsofflinefirst.HackerNewsOfflineFirstApp
import com.example.jll.hackernewsofflinefirst.IndexFragment
import com.example.jll.hackernewsofflinefirst.di.modules.AppModule
import com.example.jll.hackernewsofflinefirst.di.modules.BuildersModule
import com.example.jll.hackernewsofflinefirst.di.modules.FragmentModule
import com.example.jll.hackernewsofflinefirst.network.NetworkModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
  modules = [
    AndroidInjectionModule::class,
    BuildersModule::class,
    FragmentModule::class,
    AppModule::class,
    NetworkModule::class
  ]
)
interface AppComponent {

 /* @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: Application): Builder

    fun build(): AppComponent
  } */

  fun inject(app: HackerNewsOfflineFirstApp)
  fun inject(fragment: IndexFragment)
}
