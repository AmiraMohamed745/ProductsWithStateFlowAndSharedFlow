package com.example.productswithcoroutines.model

import com.example.productswithcoroutines.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    suspend fun getAllProductsFromAPI(): Flow<List<Product>>

    fun getAllProductsFromDatabase(): Flow<List<Product>>

    suspend fun insertProduct(product: Product): Long

    suspend fun deleteProduct(product: Product?): Int
}