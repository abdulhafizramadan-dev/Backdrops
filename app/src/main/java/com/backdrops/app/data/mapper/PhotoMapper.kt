package com.backdrops.app.data.mapper

import com.backdrops.app.data.local.entity.PhotoEntity
import com.backdrops.app.data.network.response.PhotoResponse
import com.backdrops.app.domain.model.PhotoItem
import com.backdrops.app.domain.model.PhotoItemType
import com.backdrops.app.util.toRelativeDateFormat

@JvmName("responseToDomain")
fun List<PhotoResponse>.toDomains(): List<PhotoItem> = map {
    it.toDomain()
}

fun PhotoResponse.toDomain(): PhotoItem {
    val description = description ?: altDescription ?: ""
    return PhotoItem(
        id = id ?: "",
        photo = urls?.regular ?: "",
        color = color ?: "",
        description = description,
        user = user?.name ?: "",
        createdAt = (createdAt ?: "").toRelativeDateFormat()
    )
}

@JvmName("responseToEntities")
fun List<PhotoResponse>.toEntities(type: PhotoItemType): List<PhotoEntity> = map {
    it.toEntity(type)
}

fun PhotoResponse.toEntity(type: PhotoItemType): PhotoEntity {
    val description = description ?: altDescription ?: ""
    return PhotoEntity(
        id = id ?: "",
        type = type,
        photo = urls?.regular ?: "",
        color = color ?: "",
        description = description,
        user = user?.name ?: "",
        createdAt = createdAt ?: ""
    )
}

@JvmName("entityToDomain")
fun List<PhotoEntity>.toDomains(): List<PhotoItem> = map {
    it.toDomain()
}

fun PhotoEntity.toDomain(): PhotoItem {
    return PhotoItem(
        id = id,
        photo = photo,
        color = color,
        description = description,
        user = user,
        createdAt = createdAt.toRelativeDateFormat(),
        isFavorite = isFavorite
    )
}
