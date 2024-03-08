package com.backdrops.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.backdrops.app.data.local.dao.PhotoDao
import com.backdrops.app.data.local.dao.RemoteKeyDao
import com.backdrops.app.data.local.entity.PhotoEntity
import com.backdrops.app.data.local.entity.RemoteKey

@Database(entities = [PhotoEntity::class, RemoteKey::class], version = 1)
abstract class BackdropsDatabase : RoomDatabase() {

    abstract val photoDao: PhotoDao
    abstract val remoteKeyDao: RemoteKeyDao

}