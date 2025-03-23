package com.example.productswithcoroutines.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.productswithcoroutines.Product
import com.example.productswithcoroutines.model.ProductsRepository
import com.example.productswithcoroutines.network.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteProductViewModel(private val repo: ProductsRepository) : ViewModel() {

    private val mutableFavoriteProducts = MutableStateFlow<Response>(Response.Loading)
    val favoriteProducts = mutableFavoriteProducts.asStateFlow()

    private val mutableMessage = MutableSharedFlow<String>()
    val message = mutableMessage.asSharedFlow()

    fun getProducts() {
        viewModelScope.launch {
            try {
                val result = repo.getAllProductsFromDatabase()
                result
                    .catch { exception ->
                        mutableFavoriteProducts.value = Response.Failure(exception)
                    }
                    .collect {
                        mutableFavoriteProducts.value = Response.Success(it)
                    }
            } catch (ex: Exception) {
                mutableFavoriteProducts.value = Response.Failure(ex)
                mutableMessage.emit("Error: ${ex.message}")
            }
        }
    }

    fun deleteProductFromFavorite(product: Product?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repo.deleteProduct(product)
                if (result > 0) {
                    mutableMessage.emit("Deleted successfully!")
                } else {
                    mutableMessage.emit("Couldn't delete product!")
                }
            } catch (ex: Exception) {
                mutableMessage.emit("Error occurred: ${ex.message}")
            }

        }
    }

}

class FavoriteProductsFactory(private val _repo: ProductsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavoriteProductViewModel::class.java)) {
            FavoriteProductViewModel(_repo) as T
        } else {
            throw IllegalArgumentException("View class not found.")
        }
    }
}