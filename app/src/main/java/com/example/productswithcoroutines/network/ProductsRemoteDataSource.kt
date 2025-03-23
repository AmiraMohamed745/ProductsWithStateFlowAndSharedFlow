package com.example.productswithcoroutines.network

import com.example.productswithcoroutines.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProductsRemoteDataSource(private val service: ProductService): RemoteDataSource {

    override suspend fun getAllProducts(): Flow<List<Product>> {
        return flowOf(service.getAllProducts().products)
    }

}