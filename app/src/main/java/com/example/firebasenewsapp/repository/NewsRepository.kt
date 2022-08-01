package com.example.firebasenewsapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.firebasenewsapp.models.NewsFeedItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NewsRepository {

    val database = Firebase.database
    val newsFeedReference = database.getReference("news_feed")

    fun fetchNewsFeed(liveData: MutableLiveData<List<NewsFeedItem>>) {
        newsFeedReference
            .orderByChild("_rank")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val newsFeedItems: List<NewsFeedItem> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(NewsFeedItem::class.java)!!.copy(id = snapshot.key!!)
                    }
                    liveData.postValue(newsFeedItems)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    fun updateFavoriteStatus(id: String, isFavorite: Boolean) {
        newsFeedReference.child(id).child("favorite").setValue(isFavorite)
    }
}