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

class AllProductsViewModel(private val repo: ProductsRepository) : ViewModel() {

    private val mutableProducts = MutableStateFlow<Response>(Response.Loading)
    val products = mutableProducts.asStateFlow()

    private val mutableMessage = MutableSharedFlow<String>()
    val message = mutableMessage.asSharedFlow()

    fun getProducts() {
        viewModelScope.launch {
            try {
                val result = repo.getAllProductsFromAPI()
                result
                    .catch { exception ->
                        mutableProducts.value = Response.Failure(exception)
                    }
                    .collect {
                        mutableProducts.value = Response.Success(it)
                    }
            } catch (ex: Exception) {
                mutableProducts.value = Response.Failure(ex)
                mutableMessage.emit("Error: ${ex.message}")
            }
        }
    }

    fun addProductToFavorite(product: Product?) {
        if (product != null) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val result = repo.insertProduct(product)
                    if (result > 0) {
                        mutableMessage.emit("Added successfully!")
                    } else {
                        mutableMessage.emit("Product is already in favorites!")
                    }
                } catch (ex: Exception) {
                    mutableMessage.emit("Error occurred: ${ex.message}")
                }
            }
        } else {
            //
        }
    }

}

class AllProductsFactory(private val _repo: ProductsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AllProductsViewModel::class.java)) {
            AllProductsViewModel(_repo) as T
        } else {
            throw IllegalArgumentException("View class not found.")
        }
    }
}