package com.backdrops.app.di

import com.backdrops.app.domain.interactor.PhotoInteractor
import com.backdrops.app.domain.usecase.PhotoUseCase
import org.koin.dsl.module

val domainModule = module {
    single<PhotoUseCase> { PhotoInteractor(get()) }
}
