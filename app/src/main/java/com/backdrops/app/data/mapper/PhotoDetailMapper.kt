package com.backdrops.app.data.mapper

import com.backdrops.app.data.network.response.PhotoDetailResponse
import com.backdrops.app.data.network.response.PhotoResponse
import com.backdrops.app.domain.model.PhotoDetail
import com.backdrops.app.domain.model.PhotoItem

fun PhotoDetailResponse.toDomain(): PhotoDetail {
    val description = description ?: altDescription ?: ""
    val dimension = "$height x $width"
    return PhotoDetail(
        id = id ?: "",
        photo = urls?.regular ?: "",
        color = color ?: "",
        description = description,
        userName = user?.name ?: "",
        userPhoto = user?.profileImage?.medium ?: "",
        downloads = downloads ?: 0,
        dimension = dimension,
        links = links?.html ?: "",
        views = views ?: 0
    )
}
