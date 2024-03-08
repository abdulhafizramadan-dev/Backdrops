package com.backdrops.app.data.network.response

import com.google.gson.annotations.SerializedName

data class LinksResponse(

	@field:SerializedName("download")
	val download: String? = null,

	@field:SerializedName("download_location")
	val downloadLocation: String? = null,

	@field:SerializedName("self")
	val self: String? = null,

	@field:SerializedName("html")
	val html: String? = null
)
