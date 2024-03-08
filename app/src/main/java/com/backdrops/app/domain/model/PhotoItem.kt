package com.backdrops.app.domain.model

enum class PhotoItemType(val route: String) {
	Popular("popular"), Latest("latest"), Oldest("oldest"), Carousel("")
}

data class PhotoItem(

	val id: String = "",

	val type: PhotoItemType = PhotoItemType.Latest,

	val photo: String = "",

	val color: String = "",

	val description: String = "",

	val user: String = "",

	val createdAt: String = "",

	val isFavorite: Boolean = false

)

