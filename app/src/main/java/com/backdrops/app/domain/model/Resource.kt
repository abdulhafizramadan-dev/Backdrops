package com.backdrops.app.domain.model

sealed interface Resource<out T> {
    data object Loading : Resource<Nothing>
    data class Success<T>(val data: T) : Resource<T>
    data class Error<T>(val data: T? = null, val message: String) : Resource<T>
}