package com.backdrops.app.ui.gallery

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.backdrops.app.R
import com.backdrops.app.databinding.ItemGalleryBinding
import com.backdrops.app.domain.model.PhotoItem
import com.backdrops.app.presentation.detail_photo.DetailPhotoActivity
import com.backdrops.app.util.PhotoItemComparator

class GalleryPagingDataAdapter : PagingDataAdapter<PhotoItem, GalleryPagingDataAdapter.GalleryPagingDataAdapterViewHolder>(PhotoItemComparator) {

    inner class GalleryPagingDataAdapterViewHolder(private val binding: ItemGalleryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: PhotoItem) {
            with(binding) {
                ivGallery.load(photo.photo) {
                    placeholder(R.drawable.placeholder_gallery)
                    error(R.drawable.error_gallery)
                }

                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    itemView.context as Activity,
                    Pair(binding.ivGallery, "photo")
                )
                val toDetailPhotoActivity = Intent(itemView.context, DetailPhotoActivity::class.java)
                toDetailPhotoActivity.putExtra(DetailPhotoActivity.EXTRA_PHOTO, photo.photo)
                root.setOnClickListener {
                    itemView.context.startActivity(toDetailPhotoActivity, optionsCompat.toBundle())
                }
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