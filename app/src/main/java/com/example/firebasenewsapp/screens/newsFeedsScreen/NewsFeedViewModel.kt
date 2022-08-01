package com.example.firebasenewsapp.screens.newsFeedsScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasenewsapp.models.NewsFeedItem
import com.example.firebasenewsapp.repository.NewsRepository

class NewsFeedViewModel: ViewModel() {

    private val repository = NewsRepository()

    private val _newsFeedLiveData = MutableLiveData<List<NewsFeedItem>>()
    val newsFeedLiveData: LiveData<List<NewsFeedItem>> = _newsFeedLiveData

    fun fetchNewsFeed() {
        repository.fetchNewsFeed(_newsFeedLiveData)
    }

    fun updateFavoriteStatus(id: String, isFavorite: Boolean) {
        repository.updateFavoriteStatus(id, isFavorite)
    }

}