package com.backdrops.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.backdrops.app.domain.model.PhotoItemType

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: PhotoItemType,
    val photoId: String,
    val prevKey: Int?,
    val currentKey: Int,
    val nextKey: Int?
)