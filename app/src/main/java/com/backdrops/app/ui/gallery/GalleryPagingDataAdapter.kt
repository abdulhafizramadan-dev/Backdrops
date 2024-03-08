package com.backdrops.app.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.backdrops.app.R
import com.backdrops.app.databinding.ItemGalleryBinding
import com.backdrops.app.domain.model.PhotoItem
import com.backdrops.app.util.PhotoItemComparator
import com.bumptech.glide.Glide

class GalleryPagingDataAdapter : PagingDataAdapter<PhotoItem, GalleryPagingDataAdapter.GalleryPagingDataAdapterViewHolder>(PhotoItemComparator) {

    inner class GalleryPagingDataAdapterViewHolder(private val binding: ItemGalleryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: PhotoItem) {
            with(binding) {
                Glide.with(root)
                    .load(photo.photo)
                    .placeholder(R.drawable.placeholder_gallery)
                    .error(R.drawable.error_gallery)
                    .into(ivGallery)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryPagingDataAdapterViewHolder {
        val binding = ItemGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryPagingDataAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryPagingDataAdapterViewHolder, position: Int) {
        val photo = getItem(position)
        if (photo != null) {
            holder.bind(photo)
        }
    }
}