package com.backdrops.app.presentation.detail_photo

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.backdrops.app.R
import com.backdrops.app.databinding.ActivityDetailPhotoBinding

class DetailPhotoActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PHOTO = "extra_photo"
        const val EXTRA_COLOR = "extra_color"
    }

    private lateinit var binding: ActivityDetailPhotoBinding
    private var photo = ""
    private var color = "#000"

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        photo = intent.getStringExtra(EXTRA_PHOTO) ?: ""
        color = intent.getStringExtra(EXTRA_COLOR) ?: color

        binding.root.setBackgroundColor(Color.parseColor(color))
        binding.ivPhoto.transitionName = photo

        binding.ivPhoto.load(photo) {
            placeholder(R.drawable.placeholder_gallery)
            error(R.drawable.error_gallery)
        }

    }
}