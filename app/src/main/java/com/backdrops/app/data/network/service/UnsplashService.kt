package com.backdrops.app.data.network.service

import com.backdrops.app.data.network.response.CommonErrorResponse
import com.backdrops.app.data.network.response.PhotoDetailResponse
import com.backdrops.app.data.network.response.PhotoResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashService {

    @GET("photos")
    suspend fun listPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") orderBy: String
    ): NetworkResponse<List<PhotoResponse>, CommonErrorResponse>

    @GET("photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") orderBy: String
    ): NetworkResponse<List<PhotoResponse>, CommonErrorResponse>

    @GET("photos/:id")
    suspend fun getPhoto(
        @Path("id") id: String
    ): NetworkResponse<PhotoDetailResponse, CommonErrorResponse>

}

