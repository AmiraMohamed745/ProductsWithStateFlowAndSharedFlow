package com.example.productswithcoroutines.network

import com.example.productswithcoroutines.Product
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun getAllProducts(): Flow<List<Product>>
}