package com.example.jll.hackernewsofflinefirst.viewmodels


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

import com.example.jll.hackernewsofflinefirst.R
import com.example.jll.hackernewsofflinefirst.utils.Utils.DETAIL_FRAGMENT_URL
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

  lateinit var mWebView: WebView
  lateinit var mUrl: String

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    mUrl = arguments!!.getString(DETAIL_FRAGMENT_URL)
    return inflater.inflate(R.layout.fragment_detail, container, false)
  }

  override fun onStart() {
    super.onStart()

    goBackBtn.setOnClickListener {
      fragmentManager!!.popBackStack()
    }

    mWebView = detailWebView
    mWebView.webViewClient = object : WebViewClient() {
      override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return super.shouldOverrideUrlLoading(view, request)
      }
    }
    mWebView.loadUrl(mUrl)

  }

  companion object {
    internal fun newInstance(url: String): DetailFragment {
      val f = DetailFragment()

      val args = Bundle()
      args.putString(DETAIL_FRAGMENT_URL, url)
      f.arguments = args

      return f
    }
  }

}
