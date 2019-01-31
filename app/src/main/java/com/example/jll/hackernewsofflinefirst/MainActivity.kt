package com.example.jll.hackernewsofflinefirst

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.example.jll.hackernewsofflinefirst.adapters.ArticlesAdapter
import com.example.jll.hackernewsofflinefirst.models.Article
import com.example.jll.hackernewsofflinefirst.other.SwipeToDeleteHandler
import com.example.jll.hackernewsofflinefirst.viewmodels.ArticlesViewModel
import com.example.jll.hackernewsofflinefirst.viewmodels.ArticlesViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

  @Inject
  lateinit var mArticlesVMFactory: ArticlesViewModelFactory
  lateinit var mArticlesVM: ArticlesViewModel
  lateinit var mLayoutManager: LinearLayoutManager
  lateinit var mAdapter: ArticlesAdapter
  lateinit var mRecyclerView: RecyclerView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    AndroidInjection.inject(this)

    mArticlesVM = ViewModelProviders.of(this, mArticlesVMFactory).get(ArticlesViewModel::class.java)
    mArticlesVM.fetchArticles()

    mArticlesVM.articlesResult().observe(this,
      Observer<List<Article>> {
        refreshRecyclerView(it!!)
      })

    mArticlesVM.articlesError().observe(this,
      Observer<String> {
        System.out.println(it)
      }
    )

    mLayoutManager = LinearLayoutManager(this)
    mAdapter = ArticlesAdapter()
    mRecyclerView = articlesRV

    mRecyclerView.addItemDecoration(DividerItemDecoration(mRecyclerView.context, mLayoutManager.orientation))

    mRecyclerView.apply {
      layoutManager = mLayoutManager
      adapter = mAdapter
    }

    enableSwipeToDeleteAndUndo()
  }


  private fun enableSwipeToDeleteAndUndo() {
    val swipeToDeleteHandler = object : SwipeToDeleteHandler(this) {
      override fun onSwiped(@NonNull viewHolder: RecyclerView.ViewHolder, i: Int) {


        val position = viewHolder.adapterPosition
        val article = mAdapter.getItems()[position]
        //mAdapter.removeItem(position)
        mArticlesVM.deleteArticle(article)

        /*
        val snackbar = Snackbar
          .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG)
        snackbar.setAction("UNDO", object : View.OnClickListener() {
          fun onClick(view: View) {

            mAdapter.restoreItem(item, position)
            recyclerView.scrollToPosition(position)
          }
        })

        snackbar.setActionTextColor(Color.YELLOW)
        snackbar.show()

        */

      }
    }

    val itemTouchhelper = ItemTouchHelper(swipeToDeleteHandler)
    itemTouchhelper.attachToRecyclerView(articlesRV)
  }


  fun refreshRecyclerView(articles: List<Article>) {
    mAdapter.refreshItems(articles)
  }


  override fun onDestroy() {
    mArticlesVM.disposeElements()
    super.onDestroy()
  }

}
