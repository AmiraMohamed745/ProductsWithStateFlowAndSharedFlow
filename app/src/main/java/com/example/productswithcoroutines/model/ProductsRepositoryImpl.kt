package com.example.productswithcoroutines.model

import com.example.productswithcoroutines.Product
import com.example.productswithcoroutines.db.LocalDataSource
import com.example.productswithcoroutines.network.RemoteDataSource
import kotlinx.coroutines.flow.Flow

class ProductsRepositoryImpl private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ProductsRepository {

    override suspend fun getAllProductsFromAPI(): Flow<List<Product>> {
        return remoteDataSource.getAllProducts()
    }

    override fun getAllProductsFromDatabase(): Flow<List<Product>> {
        return localDataSource.getAllProducts()
    }

    override suspend fun insertProduct(product: Product): Long {
        return localDataSource.insertProduct(product)
    }

    override suspend fun deleteProduct(product: Product?): Int {
        return localDataSource.deleteProduct(product)
    }

    companion object {
        private var productRepoImplInstance: ProductsRepositoryImpl? = null
        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource
        ): ProductsRepositoryImpl {
            return productRepoImplInstance ?: synchronized(this) {
                val temp = ProductsRepositoryImpl(remoteDataSource, localDataSource)
                productRepoImplInstance = temp
                temp
            }
        }
    }
}