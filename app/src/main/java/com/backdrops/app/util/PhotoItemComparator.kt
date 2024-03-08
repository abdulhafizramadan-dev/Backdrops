package com.backdrops.app.util

import androidx.recyclerview.widget.DiffUtil
import com.backdrops.app.domain.model.PhotoItem

object PhotoItemComparator : DiffUtil.ItemCallback<PhotoItem>() {
    override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
        return oldItem.description == newItem.description
    }
}
