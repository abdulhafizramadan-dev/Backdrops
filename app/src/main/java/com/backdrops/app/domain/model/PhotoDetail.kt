package com.backdrops.app.domain.model

data class PhotoDetail(

	val id: String = "",

	val photo: String = "",

	val color: String = "",

	val description: String = "",

	val userName: String = "",

	val userPhoto: String = "",

	val downloads: Int = 0,

	val dimension: String = "",

	val links: String = "",

	val views: Int = 0,

	val isFavorite: Boolean = false
)
