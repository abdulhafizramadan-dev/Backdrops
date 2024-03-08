package com.backdrops.app.di

import com.backdrops.app.presentation.popular.PopularViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val popularModule = module {
    viewModel { PopularViewModel(get()) }
}
