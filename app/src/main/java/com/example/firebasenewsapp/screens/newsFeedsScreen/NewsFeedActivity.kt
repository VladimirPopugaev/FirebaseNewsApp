package com.example.firebasenewsapp.screens.newsFeedsScreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasenewsapp.R
import com.example.firebasenewsapp.databinding.ActivityNewsFeedsBinding
import com.example.firebasenewsapp.models.NewsFeedItem
import com.example.firebasenewsapp.screens.newsDetailScreen.NewsDetailActivity
import java.lang.ref.WeakReference

class NewsFeedActivity : AppCompatActivity(), NewsFeedRecyclerViewAdapter.NewsFeedItemInterface {

    private val viewModel: NewsFeedViewModel by lazy {
        ViewModelProvider(this)[NewsFeedViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_feeds)

        val binding: ActivityNewsFeedsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_news_feeds)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel


        // create adapter for recyclerView
        val newsFeedRecyclerViewAdapter = NewsFeedRecyclerViewAdapter(WeakReference(this))
        binding.recyclerView.adapter = newsFeedRecyclerViewAdapter

        viewModel.fetchNewsFeed()
    }

    // region NewsFeedRecyclerViewAdapter.NewsFeedItemInterface
    override fun onNewsFeedItemClicked(url: String) {
        val intent = Intent(this, NewsDetailActivity::class.java).apply {
            putExtra(NewsDetailActivity.ARG_URL, url)
        }

        startActivity(intent)
    }

    override fun onFavoriteStatusChanged(newsFeedItemId: String, newStatus: Boolean) {
        viewModel.updateFavoriteStatus(newsFeedItemId, newStatus)
    }
    // endregion NewsFeedRecyclerViewAdapter.NewsFeedItemInterface
}

