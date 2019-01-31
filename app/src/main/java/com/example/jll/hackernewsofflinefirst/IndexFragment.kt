package com.example.jll.hackernewsofflinefirst


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jll.hackernewsofflinefirst.adapters.ArticlesAdapter
import com.example.jll.hackernewsofflinefirst.models.Article
import com.example.jll.hackernewsofflinefirst.other.FragmentNavigationInterface
import com.example.jll.hackernewsofflinefirst.other.SwipeToDeleteHandler
import com.example.jll.hackernewsofflinefirst.viewmodels.ArticlesViewModel
import com.example.jll.hackernewsofflinefirst.viewmodels.ArticlesViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_index.*
import javax.inject.Inject


class IndexFragment : Fragment() {

  @Inject
  lateinit var mArticlesVMFactory: ArticlesViewModelFactory
  private lateinit var mArticlesVM: ArticlesViewModel
  private lateinit var mLayoutManager: LinearLayoutManager
  private lateinit var mAdapter: ArticlesAdapter
  private lateinit var mRecyclerView: RecyclerView
  private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    AndroidSupportInjection.inject(this)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    mArticlesVM = activity?.run {
      ViewModelProviders.of(this, mArticlesVMFactory).get(ArticlesViewModel::class.java)
    } ?: throw Exception("Invalid activity")
    return inflater.inflate(R.layout.fragment_index, container, false)
  }

  override fun onStart() {
    super.onStart()

    mArticlesVM = ViewModelProviders.of(this, mArticlesVMFactory).get(ArticlesViewModel::class.java)

    setUpSwipeRefresh()
    setUpRecyclerView()
    enableSwipeToDeleteAndUndo()

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

    mArticlesVM.refreshFinishes().observe(this,
      Observer<Boolean> {
        mSwipeRefreshLayout.isRefreshing = false
      }
    )
  }


  private fun setUpSwipeRefresh() {
    mSwipeRefreshLayout = swipeRefreshLayout
    mSwipeRefreshLayout.setOnRefreshListener {
      mArticlesVM.fetchArticles()
    }
  }

  private fun navToDetail(): (String) -> Unit {
    return fun(url: String) {
      val activityListener = context as FragmentNavigationInterface
      activityListener.goToDetail(url)
    }
  }

  private fun setUpRecyclerView() {
    mLayoutManager = LinearLayoutManager(context!!)
    mAdapter = ArticlesAdapter(navToDetail())
    mRecyclerView = articlesRV

    mRecyclerView.addItemDecoration(DividerItemDecoration(mRecyclerView.context, mLayoutManager.orientation))

    mRecyclerView.apply {
      layoutManager = mLayoutManager
      adapter = mAdapter
    }

  }


  private fun enableSwipeToDeleteAndUndo() {
    val swipeToDeleteHandler = object : SwipeToDeleteHandler(context!!) {
      override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
        val position = viewHolder.adapterPosition
        val article = mAdapter.getItems()[position]
        mArticlesVM.deleteArticle(article)
      }
    }

    val itemTouchhelper = ItemTouchHelper(swipeToDeleteHandler)
    itemTouchhelper.attachToRecyclerView(articlesRV)
  }


  fun refreshRecyclerView(articles: List<Article>) {
    mAdapter.refreshItems(articles)
  }

  override fun onDestroy() {
    super.onDestroy()
    mArticlesVM.disposeElements()
  }

}
