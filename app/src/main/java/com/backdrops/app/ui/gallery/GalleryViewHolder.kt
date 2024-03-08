package com.backdrops.app.ui.gallery

import android.content.Intent
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.backdrops.app.MainActivity
import com.backdrops.app.R
import com.backdrops.app.databinding.ItemGalleryBinding
import com.backdrops.app.domain.model.PhotoItem
import com.backdrops.app.presentation.detail_photo.DetailPhotoActivity

class GalleryViewHolder(private val binding: ItemGalleryBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(photo: PhotoItem) {
        with(binding) {
            ivGallery.transitionName = photo.photo
            ivGallery.load(photo.photo) {
                placeholder(R.drawable.placeholder_gallery)
                error(R.drawable.error_gallery)
            }

            val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                itemView.context as MainActivity,
                Pair(binding.ivGallery, photo.photo)
            )
            val toDetailPhotoActivity = Intent(itemView.context, DetailPhotoActivity::class.java)
            toDetailPhotoActivity.putExtra(DetailPhotoActivity.EXTRA_PHOTO, photo.photo)
            root.setOnClickListener {
                itemView.context.startActivity(toDetailPhotoActivity, optionsCompat.toBundle())
            }
        }
    }
}

