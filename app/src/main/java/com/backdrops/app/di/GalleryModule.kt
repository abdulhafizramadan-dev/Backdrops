package com.backdrops.app.di

import com.backdrops.app.presentation.gallery.GalleryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val galleryModule = module {
    viewModel { GalleryViewModel(get()) }
}
