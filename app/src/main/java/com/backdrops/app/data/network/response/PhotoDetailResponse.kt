package com.backdrops.app.data.network.response

import com.google.gson.annotations.SerializedName

data class PhotoDetailResponse(

	@field:SerializedName("urls")
	val urls: UrlsResponse? = null,

	@field:SerializedName("alt_description")
	val altDescription: String? = null,

	@field:SerializedName("color")
	val color: String? = null,

	@field:SerializedName("downloads")
	val downloads: Int? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("links")
	val links: LinksResponse? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("user")
	val user: UserResponse? = null,

	@field:SerializedName("views")
	val views: Int? = null,

	@field:SerializedName("height")
	val height: Int? = null
)
