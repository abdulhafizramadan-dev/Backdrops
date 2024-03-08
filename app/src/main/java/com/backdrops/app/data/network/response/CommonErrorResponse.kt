package com.backdrops.app.data.network.response

import com.google.gson.annotations.SerializedName

data class CommonErrorResponse(

	@field:SerializedName("errors")
	val errors: List<String>? = null
)
