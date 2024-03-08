package com.backdrops.app.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.backdrops.app.databinding.ItemGalleryBinding
import com.backdrops.app.domain.model.PhotoItem
import com.backdrops.app.util.PhotoItemComparator

class GalleryAdapter : ListAdapter<PhotoItem, GalleryViewHolder>(PhotoItemComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val binding = ItemGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val photo = getItem(position)
        if (photo != null) {
            holder.bind(photo)
        }
    }
}