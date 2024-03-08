package com.backdrops.app.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.backdrops.app.data.mapper.toDomains
import com.backdrops.app.data.network.service.UnsplashService
import com.backdrops.app.domain.model.PhotoItem
import com.backdrops.app.domain.model.PhotoItemType
import com.backdrops.app.util.wrapEspressoIdlingResource
import com.haroldadmin.cnradapter.NetworkResponse

class SearchPhotoPagingSource(
    private val apiService: UnsplashService,
    private val query: String,
    private val type: PhotoItemType,
) : PagingSource<Int, PhotoItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoItem> {
        return wrapEspressoIdlingResource {
            try {
                val nextPageNumber = params.key ?: 1
                val newsResponse = apiService.searchPhotos(
                    query = query,
                    page = nextPageNumber,
                    perPage = params.loadSize,
                    orderBy = type.route
                )
                when (newsResponse) {
                    is NetworkResponse.Success -> {
                        val newsDomain = newsResponse.body.toDomains()
                        LoadResult.Page(
                            data = newsDomain,
                            prevKey = null,
                            nextKey = nextPageNumber + 1
                        )
                    }
                    is NetworkResponse.Error -> {
                        val message = newsResponse.body?.errors?.getOrNull(0) ?: newsResponse.error?.message
                        LoadResult.Error(Throwable(message))
                    }
                }
            } catch (e: Exception) {
                LoadResult.Error(e)
            }

        }
    }

    override fun getRefreshKey(state: PagingState<Int, PhotoItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}