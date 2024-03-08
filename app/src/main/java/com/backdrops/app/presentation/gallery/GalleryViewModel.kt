package com.backdrops.app.presentation.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.backdrops.app.domain.model.PhotoItem
import com.backdrops.app.domain.model.PhotoItemType
import com.backdrops.app.domain.model.Resource
import com.backdrops.app.domain.usecase.PhotoUseCase
import kotlinx.coroutines.launch

class GalleryViewModel(
    private val photoUseCase: PhotoUseCase
) : ViewModel() {

    private val _gallery = MutableLiveData<Resource<List<PhotoItem>>>()
    val gallery: LiveData<Resource<List<PhotoItem>>> get() = _gallery

    fun getGallery() {
        viewModelScope.launch {
            photoUseCase.listPhotos(type = PhotoItemType.Latest, page = 1, perPage = 100).collect { resource ->
                _gallery.postValue(resource)
            }
        }
    }

}