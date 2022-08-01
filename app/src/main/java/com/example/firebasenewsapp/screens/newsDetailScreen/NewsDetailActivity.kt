package com.example.firebasenewsapp.screens.newsDetailScreen

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firebasenewsapp.R
import com.example.firebasenewsapp.databinding.ActivityNewsDetailBinding

class NewsDetailActivity : AppCompatActivity() {

    companion object {
        const val ARG_URL = "ARG_URL_NEWS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityNewsDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_news_detail)

        val client = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.contentLoadingProgressBar.hide()
            }
        }

        binding.webView.webViewClient = client

        binding.url = intent.getStringExtra(ARG_URL) ?: ""
    }

}