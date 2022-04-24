package com.borshevskiy.googlebooksapp.data.network

import com.borshevskiy.googlebooksapp.data.network.model.ApiResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("volumes")
    suspend fun getBooks(
        @Query("q") q: String
    ): Response<ApiResponseDto>
}