package com.example.productswithcoroutines.db

import com.example.productswithcoroutines.Product
import kotlinx.coroutines.flow.Flow

class ProductsLocalDataSource(private val dao: ProductDAO) : LocalDataSource {

    override fun getAllProducts(): Flow<List<Product>> {
        return dao.getAllProducts()
    }

    override suspend fun insertProduct(product: Product): Long {
        return dao.insertProduct(product)
    }

    override suspend fun deleteProduct(product: Product?): Int {
        return if (product != null)
            dao.deleteProduct(product)
        else
            -1

    }
}