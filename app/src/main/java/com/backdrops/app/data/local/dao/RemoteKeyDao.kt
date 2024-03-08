package com.backdrops.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.backdrops.app.data.local.entity.RemoteKey
import com.backdrops.app.domain.model.PhotoItemType

@Dao
interface RemoteKeyDao {
    @Upsert
    suspend fun upsertRemoteKeys(remoteKeys: List<RemoteKey>)

    @Query("SELECT * FROM remote_keys WHERE photoId = :photoId")
    suspend fun getRemoteKeyByPhotoId(photoId: String): RemoteKey

    @Query("DELETE FROM remote_keys WHERE type = :type")
    suspend fun clearRemoteKeys(type: PhotoItemType)
}