package com.example.retrofit.api

import com.example.retrofit.model.GalleryImg
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GalleryAPIs {

    @GET("?key=39785681-f2baf41ceae85ce529ef7a2a3&category=")
    suspend fun getservice(@Query("category") category: String,@Query("page") page:Int ): Response<GalleryImg>

}