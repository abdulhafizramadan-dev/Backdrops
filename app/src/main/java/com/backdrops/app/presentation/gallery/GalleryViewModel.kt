package com.backdrops.app.presentation.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.backdrops.app.domain.model.PhotoItem
import com.backdrops.app.domain.model.PhotoItemType
import com.backdrops.app.domain.usecase.PhotoUseCase

class GalleryViewModel(
    private val photoUseCase: PhotoUseCase
) : ViewModel() {

//    private val _gallery = MutableLiveData<PagingData<PhotoItem>>()
    val gallery: LiveData<PagingData<PhotoItem>> get() = photoUseCase.listPhotosPagingData(PhotoItemType.Oldest).cachedIn(viewModelScope)

}