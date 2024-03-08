package com.backdrops.app.domain.interactor

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.backdrops.app.domain.model.PhotoDetail
import com.backdrops.app.domain.model.PhotoItem
import com.backdrops.app.domain.model.PhotoItemType
import com.backdrops.app.domain.model.Resource
import com.backdrops.app.domain.repository.PhotoRepository
import com.backdrops.app.domain.usecase.PhotoUseCase

class PhotoUseInteractor(
    private val repository: PhotoRepository
) : PhotoUseCase {

    override fun listPhotos(type: PhotoItemType): LiveData<Resource<List<PhotoItem>>> {
        return repository.listPhotos(type)
    }

    override fun listPhotosPagingData(type: PhotoItemType): LiveData<PagingData<PhotoItem>> {
        return repository.listPhotosPagingData(type)
    }

    override fun searchPhotosPagingData(
        query: String,
        type: PhotoItemType
    ): LiveData<PagingData<PhotoItem>> {
        return repository.searchPhotosPagingData(query, type)
    }

    override fun getPhoto(id: String): LiveData<Resource<PhotoDetail>> {
        return repository.getPhoto(id)
    }
}