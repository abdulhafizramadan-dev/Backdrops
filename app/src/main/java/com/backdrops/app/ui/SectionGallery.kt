package com.backdrops.app.ui

import android.view.View
import com.backdrops.app.R
import com.backdrops.app.databinding.SectionGalleryBinding
import com.backdrops.app.domain.model.PhotoItem
import com.backdrops.app.ui.gallery.GalleryAdapter
import com.xwray.groupie.viewbinding.BindableItem

class SectionGallery(
    private val title: String,
    private val photos: List<PhotoItem>
) : BindableItem<SectionGalleryBinding>() {

    private val adapter: GalleryAdapter by lazy {
        GalleryAdapter()
    }

    override fun getLayout(): Int = R.layout.section_gallery

    override fun initializeViewBinding(view: View): SectionGalleryBinding = SectionGalleryBinding.bind(view)

    override fun bind(viewBinding: SectionGalleryBinding, position: Int) {
        with(viewBinding) {
            tvGallery.text = title
            adapter.submitList(photos)
            rvGallery.adapter = adapter
        }
    }
}