package com.backdrops.app.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
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
import com.backdrops.app.util.wrapEspressoIdlingResource
import com.haroldadmin.cnradapter.NetworkResponse

@ExperimentalPagingApi
class PhotoRepositoryImpl(
    private val database: BackdropsDatabase,
    private val service: UnsplashService
) : PhotoRepository {

    override fun listPhotos(type: PhotoItemType): LiveData<Resource<List<PhotoItem>>> = liveData {
        wrapEspressoIdlingResource {
            emit(Resource.Loading)
            try {
                val response = service.listPhotos(
                    page = 1,
                    perPage = 10,
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
                        val resource: LiveData<Resource<List<PhotoItem>>> = dao.listPhoto(type).map {
                            Resource.Success(it.toDomains())
                        }
                        emitSource(resource)
                    }
                    is NetworkResponse.Error -> {
                        val message = response.body?.errors?.get(0) ?: response.error?.message ?: ""
                        val resource: LiveData<Resource<List<PhotoItem>>> = database.photoDao.listPhoto(type).map {
                            Resource.Error(data = it.toDomains(), message = message)
                        }
                        emitSource(resource)
                    }
                }
            } catch (error: Exception) {
                emit(Resource.Error(message = "fwe"))
            }
        }
    }

    override fun listPhotosPagingData(type: PhotoItemType): LiveData<PagingData<PhotoItem>> {
        return Pager(
            config = PagingConfig(50),
            remoteMediator = PhotoRemoteMediator(service, database, type),
            pagingSourceFactory = { database.photoDao.listPhotoPagingSource(type) }
        ).liveData.map { pagingData ->
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