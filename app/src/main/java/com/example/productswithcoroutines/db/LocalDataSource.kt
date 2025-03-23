package com.example.productswithcoroutines.db

import com.example.productswithcoroutines.Product
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun getAllProducts(): Flow<List<Product>>

    suspend fun insertProduct(product: Product): Long

    suspend fun deleteProduct(product: Product?): Int

}