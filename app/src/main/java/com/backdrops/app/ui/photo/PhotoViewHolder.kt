package com.backdrops.app.ui.photo

import android.content.Intent
import android.graphics.Color
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.backdrops.app.MainActivity
import com.backdrops.app.R
import com.backdrops.app.databinding.ItemGalleryBinding
import com.backdrops.app.databinding.ItemPhotoBinding
import com.backdrops.app.domain.model.PhotoItem
import com.backdrops.app.presentation.detail_photo.DetailPhotoActivity

class PhotoViewHolder(private val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(photo: PhotoItem) {
        with(binding) {
            ivPhoto.transitionName = photo.photo
            ivPhoto.load(photo.photo) {
                placeholder(R.drawable.placeholder_gallery)
                error(R.drawable.error_gallery)
            }
            tvTitle.text = photo.description
            tvUser.text = photo.user
            textContainer.setBackgroundColor(Color.parseColor(photo.color))

            val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                itemView.context as MainActivity,
                Pair(binding.ivPhoto, photo.photo)
            )
            val toDetailPhotoActivity = Intent(itemView.context, DetailPhotoActivity::class.java)
            toDetailPhotoActivity.putExtra(DetailPhotoActivity.EXTRA_PHOTO, photo.photo)
            toDetailPhotoActivity.putExtra(DetailPhotoActivity.EXTRA_COLOR, photo.color)
            root.setOnClickListener {
                itemView.context.startActivity(toDetailPhotoActivity, optionsCompat.toBundle())
            }
        }
    }
}

