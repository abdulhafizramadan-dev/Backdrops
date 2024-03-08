package com.backdrops.app

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.backdrops.app.di.dataModule
import com.backdrops.app.di.domainModule
import com.backdrops.app.di.galleryModule
import com.backdrops.app.di.popularModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalPagingApi
class BackdropsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(domainModule, dataModule, galleryModule, popularModule)
        }
    }

}