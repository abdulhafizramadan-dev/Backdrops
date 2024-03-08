package com.backdrops.app.presentation.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.backdrops.app.domain.model.PhotoItem
import com.backdrops.app.domain.model.PhotoItemType
import com.backdrops.app.domain.usecase.PhotoUseCase
import kotlinx.coroutines.launch

class PopularViewModel(
    private val photoUseCase: PhotoUseCase
) : ViewModel() {

    private val _photos = MutableLiveData<PagingData<PhotoItem>>()
    val photos: LiveData<PagingData<PhotoItem>> get() = _photos

    private val _isLoad = MutableLiveData(false)
    val isLoad: LiveData<Boolean> get() = _isLoad

    fun updateIsLoad(state: Boolean) {
        _isLoad.postValue(state)
    }

    fun getPhotos() {
        viewModelScope.launch {
            photoUseCase.listPhotosPagingData(type = PhotoItemType.Popular)
                .cachedIn(viewModelScope)
                .collect { resource ->
                    _photos.postValue(resource)
                }
        }
    }

}