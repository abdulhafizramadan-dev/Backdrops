package com.backdrops.app.data.network.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("profile_image")
	val profileImage: ProfileImage? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("twitter_username")
	val twitterUsername: Any? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("bio")
	val bio: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("portfolio_url")
	val portfolioUrl: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)

data class ProfileImage(

	@field:SerializedName("small")
	val small: String? = null,

	@field:SerializedName("large")
	val large: String? = null,

	@field:SerializedName("medium")
	val medium: String? = null
)
