package com.example.portal.api

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET


interface RocketApi {
    @GET("launches")
    suspend fun getRockets(): Response<ResponseBody>


}