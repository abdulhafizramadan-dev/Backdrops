package com.backdrops.app.di

import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.backdrops.app.data.PhotoRepositoryImpl
import com.backdrops.app.data.local.BackdropsDatabase
import com.backdrops.app.data.network.service.UnsplashService
import com.backdrops.app.domain.repository.PhotoRepository
import com.backdrops.app.util.ApiConfigUtil
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@ExperimentalPagingApi
val dataModule = module {
    single<OkHttpClient> {
        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", "Client-ID tuWMRaORAlaGKJKdzTlDVtzY-vhtw5vxvVOwU2JqI3I")
                .build()
            chain.proceed(requestHeaders)
        }
        OkHttpClient.Builder()
            .addInterceptor(
                ChuckerInterceptor.Builder(get())
                    .collector(ChuckerCollector(get()))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            .addInterceptor(authInterceptor)
            .build()
    }
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(ApiConfigUtil.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(get())
            .build()
    }
    single<UnsplashService> {
        get<Retrofit>().create()
    }
    single {
        Room.databaseBuilder(
            context = get(),
            klass = BackdropsDatabase::class.java,
            name = BackdropsDatabase.DATABASE_NAME
        ).build()
    }
    single<PhotoRepository> {
        PhotoRepositoryImpl(get(), get())
    }
}
