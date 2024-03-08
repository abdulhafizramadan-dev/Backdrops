package com.backdrops.app.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.backdrops.app.data.local.entity.PhotoEntity
import com.backdrops.app.domain.model.PhotoItemType

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photoentity WHERE type = :type")
    fun listPhotoPagingSource(type: PhotoItemType): PagingSource<Int, PhotoEntity>

    @Query("SELECT * FROM photoentity WHERE type = :type")
    suspend fun listPhoto(type: PhotoItemType): List<PhotoEntity>

    @Query("SELECT * FROM photoentity WHERE isFavorite = 1")
    fun listFavoritePhoto(): PagingSource<Int, PhotoEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertListPhoto(photos: List<PhotoEntity>)

    @Update
    fun updatePhoto(photo: PhotoEntity)

    @Query("DELETE FROM photoentity WHERE type = :type AND isFavorite = 0")
    fun clearListPhoto(type: PhotoItemType)

}