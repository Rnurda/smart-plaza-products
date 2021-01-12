package com.example.productssearch.api

import retrofit2.http.GET
import retrofit2.http.Query

interface SmartPlazaApi {

    companion object{
        const val BASE_URL = "https://api.smartplaza.kz/mp/"
    }

    @GET("products/products")
    suspend fun searchProducts(
      @Query("search") query: String,
      @Query("sortName") sortName: String,
      @Query("highCost") highCost: Int,
      @Query("lowCost") lowCost: Int,
      @Query("pageNumber") page: Int,
      @Query("pageSize") perPage: Int
    ): SmartPlazaResponse

    @GET("products/products")
    suspend fun filterProducts(
        @Query("sortName") sortName: String,
        @Query("highCost") highCost: Int,
        @Query("lowCost") lowCost: Int,
        @Query("pageNumber") page: Int,
        @Query("pageSize") perPage: Int
    ): SmartPlazaResponse
}