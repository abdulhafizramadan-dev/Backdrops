package com.backdrops.app.data

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import com.backdrops.app.data.local.BackdropsDatabase
import com.backdrops.app.data.mapper.toDomain
import com.backdrops.app.data.mapper.toDomains
import com.backdrops.app.data.mapper.toEntities
import com.backdrops.app.data.network.service.UnsplashService
import com.backdrops.app.data.paging.PhotoRemoteMediator
import com.backdrops.app.domain.model.PhotoDetail
import com.backdrops.app.domain.model.PhotoItem
import com.backdrops.app.domain.model.PhotoItemType
import com.backdrops.app.domain.model.Resource
import com.backdrops.app.domain.repository.PhotoRepository
import com.backdrops.app.util.toDate
import com.backdrops.app.util.wrapEspressoIdlingResource
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@ExperimentalPagingApi
class PhotoRepositoryImpl(
    private val database: BackdropsDatabase,
    private val service: UnsplashService
) : PhotoRepository {

    override fun listPhotos(
        type: PhotoItemType,
        page: Int,
        perPage: Int
    ): Flow<Resource<List<PhotoItem>>> = flow {
        wrapEspressoIdlingResource {
            emit(Resource.Loading)
            try {
                val response = service.listPhotos(
                    page = page,
                    perPage = perPage,
                    orderBy = type.route
                )
                when (response) {
                    is NetworkResponse.Success -> {
                        val dao = database.photoDao
                        database.withTransaction {
                            val entities = response.body.toEntities(type)
                            dao.clearListPhoto(type)
                            dao.insertListPhoto(entities)
                        }
                        val domains = dao.listPhoto(type).sortedByDescending { it.createdAt.toDate() }.toDomains()
                        emit(Resource.Success(data = domains))
                    }
                    is NetworkResponse.Error -> {
                        val message = response.body?.errors?.get(0) ?: response.error?.message ?: ""
                        val domains = database.photoDao.listPhoto(type).sortedByDescending { it.createdAt.toDate() }.toDomains()
                        emit(Resource.Error(data = domains, message = message))
                    }
                }
            } catch (error: Exception) {
                emit(Resource.Error(message = error.message ?: ""))
            }
        }
    }

    override fun listPhotosPagingData(type: PhotoItemType): Flow<PagingData<PhotoItem>> {
        return Pager(
            config = PagingConfig(50),
            remoteMediator = PhotoRemoteMediator(service, database, type),
            pagingSourceFactory = { database.photoDao.listPhotoPagingSource(type) }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }


    override fun searchPhotosPagingData(
        query: String,
        type: PhotoItemType
    ): LiveData<PagingData<PhotoItem>> {
        TODO("Not yet implemented")
    }

    override fun getPhoto(id: String): LiveData<Resource<PhotoDetail>> {
        TODO("Not yet implemented")
    }
}