package com.example.productswithcoroutines.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.productswithcoroutines.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDAO {

    @Query("SELECT * FROM product_table")
    fun getAllProducts(): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(product: Product): Long

    @Delete
    suspend fun deleteProduct(product: Product): Int

}