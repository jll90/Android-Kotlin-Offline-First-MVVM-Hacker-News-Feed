package com.example.jll.hackernewsofflinefirst.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.jll.hackernewsofflinefirst.R
import com.example.jll.hackernewsofflinefirst.models.Article

class ArticlesAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private val items: ArrayList<Article> = arrayListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.article_row, parent, false)
    return ArticlesAdapter.ArticleVH(view)
  }

  override fun getItemCount(): Int {
    return items.count()
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val vHolder = holder as ArticleVH
    val article = items[position]
    vHolder.title.text = article.getArticleTitle()
    vHolder.authorTimestamp.text = "${article.author} - ${article.created_at}"
  }

  class ArticleVH(view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView = view.findViewById(R.id.articleTitle)
    val authorTimestamp: TextView = view.findViewById(R.id.articleAuthorPlusRelativeTimestamp)
  }

  fun refreshItems(articles: List<Article>) {
    items.clear()
    items.addAll(articles)
    notifyDataSetChanged()
  }

  fun removeItem(position: Int) {
    items.removeAt(position)
    notifyItemRemoved(position)
  }

  fun getItems(): List<Article> {
    return items
  }
}