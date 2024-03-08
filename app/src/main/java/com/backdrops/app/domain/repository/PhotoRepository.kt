package com.backdrops.app.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.backdrops.app.domain.model.PhotoDetail
import com.backdrops.app.domain.model.PhotoItem
import com.backdrops.app.domain.model.PhotoItemType
import com.backdrops.app.domain.model.Resource

interface PhotoRepository {

    fun listPhotos(type: PhotoItemType): LiveData<Resource<List<PhotoItem>>>

    fun listPhotosPagingData(type: PhotoItemType): LiveData<PagingData<PhotoItem>>

    fun searchPhotosPagingData(query: String, type: PhotoItemType): LiveData<PagingData<PhotoItem>>

    fun getPhoto(id: String): LiveData<Resource<PhotoDetail>>

}