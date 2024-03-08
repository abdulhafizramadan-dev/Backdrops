package com.backdrops.app.ui.photo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.backdrops.app.databinding.ItemPhotoBinding
import com.backdrops.app.domain.model.PhotoItem
import com.backdrops.app.util.PhotoItemComparator

class PhotoPagingDataAdapter : PagingDataAdapter<PhotoItem, PhotoViewHolder>(PhotoItemComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = getItem(position)
        if (photo != null) {
            holder.bind(photo)
        }
    }
}