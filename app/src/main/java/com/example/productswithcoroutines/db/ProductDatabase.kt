package com.example.productswithcoroutines.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.productswithcoroutines.Constants
import com.example.productswithcoroutines.Product

@Database(entities = [Product::class], version = 3)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun getProductDao(): ProductDAO

    companion object {

        @Volatile
        private var instanceOfDb: ProductDatabase? = null

        fun getInstance(context: Context): ProductDatabase {
            return instanceOfDb ?: synchronized(this) {
                val temp: ProductDatabase = Room.databaseBuilder(
                    context, ProductDatabase::class.java,
                    Constants.PRODUCT_DATABASE
                )
                    .fallbackToDestructiveMigration()
                    .build()
                instanceOfDb = temp
                temp
            }
        }
    }

}