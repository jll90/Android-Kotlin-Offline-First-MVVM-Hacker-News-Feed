package com.example.jll.hackernewsofflinefirst

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.jll.hackernewsofflinefirst.other.FragmentNavigationInterface
import com.example.jll.hackernewsofflinefirst.viewmodels.DetailFragment


class MainActivity : AppCompatActivity(), FragmentNavigationInterface {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    goToIndex()
  }


  private fun navigateToFragment(fragment: Fragment) {
    val ft = supportFragmentManager.beginTransaction()
    ft.setCustomAnimations(R.anim.slide_in_right, android.R.anim.slide_out_right, android.R.anim.slide_in_left, R.anim.slide_out_left)
    ft.replace(R.id.mainFragmentContainer, fragment)
    if (supportFragmentManager.fragments.count() > 0){
      ft.addToBackStack(null)
    }
    ft.commit()
  }

  override fun goToIndex() {
    navigateToFragment(IndexFragment())
  }

  override fun goToDetail(url: String) {
    navigateToFragment(DetailFragment.newInstance(url))
  }
}
