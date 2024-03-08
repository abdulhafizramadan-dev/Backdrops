package com.backdrops.app.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.backdrops.app.data.local.BackdropsDatabase
import com.backdrops.app.data.local.entity.PhotoEntity
import com.backdrops.app.data.local.entity.RemoteKey
import com.backdrops.app.data.mapper.toEntities
import com.backdrops.app.data.network.service.UnsplashService
import com.backdrops.app.domain.model.PhotoItemType
import com.backdrops.app.util.wrapEspressoIdlingResource
import com.haroldadmin.cnradapter.NetworkResponse

@OptIn(ExperimentalPagingApi::class)
class PhotoRemoteMediator(
    private val apiService: UnsplashService,
    private val database: BackdropsDatabase,
    private val type: PhotoItemType
) : RemoteMediator<Int, PhotoEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotoEntity>
    ): MediatorResult {

        wrapEspressoIdlingResource {
            val page: Int = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                    nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
            }

            try {
                val newsResponse = apiService.listPhotos(
                    page = page,
                    perPage = state.config.pageSize,
                    orderBy = type.route
                )

                when (newsResponse) {
                    is NetworkResponse.Success -> {
                        val photoEntities = newsResponse.body.toEntities(type)
                        val endOfPaginationReached = photoEntities.isEmpty()

                        database.withTransaction {
                            val photoDao = database.photoDao
                            val remoteKeyDao = database.remoteKeyDao

                            if (loadType == LoadType.REFRESH) {
                                photoDao.clearListPhoto(type)
                                remoteKeyDao.clearRemoteKeys(type)
                            }

                            val prevPage = if (page > 1) page - 1 else null
                            val nextPage = if (!endOfPaginationReached) page + 1 else null

                            photoDao.insertListPhoto(photoEntities)

                            val remoteKeyEntities = photoEntities.map { news ->
                                RemoteKey(
                                    photoId = news.id,
                                    type = type,
                                    prevKey = prevPage,
                                    currentKey = page,
                                    nextKey = nextPage
                                )
                            }
                            remoteKeyDao.upsertRemoteKeys(remoteKeys = remoteKeyEntities)
                        }
                        return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                    }
                    is NetworkResponse.Error -> {
                        val message = newsResponse.body?.errors?.getOrNull(0) ?: newsResponse.error?.message
                        return MediatorResult.Error(Throwable(message))
                    }
                }
            } catch (error: Throwable) {
                return MediatorResult.Error(error)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, PhotoEntity>
    ): RemoteKey? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { news ->
            database.remoteKeyDao.getRemoteKeyByPhotoId(news.id)
        }
    }
}