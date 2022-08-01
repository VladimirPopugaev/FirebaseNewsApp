package com.example.firebasenewsapp.screens.newsFeedsScreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasenewsapp.R
import com.example.firebasenewsapp.databinding.ViewHolderNewsFeedItemBinding
import com.example.firebasenewsapp.models.NewsFeedItem
import java.lang.ref.WeakReference

class NewsFeedRecyclerViewAdapter(
    private val callbackWeakRef: WeakReference<NewsFeedItemInterface>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface NewsFeedItemInterface {
        fun onNewsFeedItemClicked(url: String)
        fun onFavoriteStatusChanged(newsFeedItemId: String, newStatus: Boolean)
    }

    private val newsFeedItems = mutableListOf<NewsFeedItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsFeedItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NewsFeedItemViewHolder).onBind(
            newsFeedItem = newsFeedItems[position],
            onClick = { url ->
                callbackWeakRef.get()?.onNewsFeedItemClicked(url)
            },
            onFavoriteChanged = { newStatus ->
                callbackWeakRef.get()
                    ?.onFavoriteStatusChanged(newsFeedItems[position].id, newStatus)
            }
        )
    }

    override fun getItemCount(): Int = newsFeedItems.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newsFeedItems: List<NewsFeedItem>?) {
        this.newsFeedItems.clear()
        this.newsFeedItems.addAll(newsFeedItems ?: emptyList())
        notifyDataSetChanged()
    }

    inner class NewsFeedItemViewHolder(
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_news_feed_item, parent, false)
    ) {
        private val binding = ViewHolderNewsFeedItemBinding.bind(itemView)

        fun onBind(
            newsFeedItem: NewsFeedItem,
            onClick: (String) -> Unit,
            onFavoriteChanged: (Boolean) -> Unit
        ) {
            binding.title = newsFeedItem.title
            binding.description = newsFeedItem.description
            binding.source = newsFeedItem.source
            binding.published = newsFeedItem.published
            binding.imageUrl = newsFeedItem.image_url

            // Favorite settings
            val drawableResId: Int = if (newsFeedItem.favorite) {
                R.drawable.ic_favorite_24
            } else {
                R.drawable.ic_baseline_favorite_border_24
            }
            binding.favoriteImageView.setImageResource(drawableResId)
            binding.favoriteImageView.setOnClickListener {
                val newStatus = !newsFeedItem.favorite
                onFavoriteChanged(newStatus)
            }

            binding.root.setOnClickListener {
                onClick(newsFeedItem.url)
            }
        }
    }
}