package com.backdrops.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.backdrops.app.domain.model.PhotoItemType

@Entity
data class PhotoEntity(

    @PrimaryKey
	val id: String = "",

    val type: PhotoItemType = PhotoItemType.Latest,

    val photo: String = "",

    val color: String = "",

    val description: String = "",

    val user: String = "",

    val createdAt: String = "",

    val isFavorite: Boolean = false
)

