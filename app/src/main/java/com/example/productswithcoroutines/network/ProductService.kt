package com.example.productswithcoroutines.network

import com.example.productswithcoroutines.network.ProductResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ProductService {
    @GET("products")
    suspend fun getAllProducts(): ProductResponse
}

object RetrofitHelper {
    private const val BASE_URL = "https://dummyjson.com/"
    private val retrofitInstance = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitService: ProductService = retrofitInstance.create(ProductService::class.java)
}