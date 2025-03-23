package com.example.productswithcoroutines

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.productswithcoroutines.db.ProductDatabase
import com.example.productswithcoroutines.db.ProductsLocalDataSource
import com.example.productswithcoroutines.model.ProductsRepositoryImpl
import com.example.productswithcoroutines.network.ProductsRemoteDataSource
import com.example.productswithcoroutines.network.Response
import com.example.productswithcoroutines.network.RetrofitHelper
import com.example.productswithcoroutines.ui.theme.ProductsWithCoroutinesTheme
import com.example.productswithcoroutines.viewmodel.FavoriteProductViewModel
import com.example.productswithcoroutines.viewmodel.FavoriteProductsFactory
import androidx.compose.runtime.getValue


class FavoriteScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductsWithCoroutinesTheme {
                ListOfFavoriteProductsUI(
                    viewModel(
                        factory = FavoriteProductsFactory(
                            ProductsRepositoryImpl.getInstance(
                                ProductsRemoteDataSource(RetrofitHelper.retrofitService),
                                ProductsLocalDataSource(
                                    ProductDatabase.getInstance(this).getProductDao()
                                )
                            )
                        )
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun FavoriteProductUI(product: Product?, onDeleteFromFavorite: () -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .clickable {
                val intent = Intent(context, DetailsScreen::class.java)
                intent.putExtra(Constants.PRODUCT_SELECTED, product)
                context.startActivity(intent)
            }
    ) {
        GlideImage(
            model = product?.thumbnail,
            contentDescription = "Image of ${product?.title}"
        )
        Text(
            text = product?.title.toString(),
            fontSize = 14.sp
        )
        Button(onClick = { onDeleteFromFavorite() }) {
            Text("Delete from favorite")
        }
    }
}

@Composable
fun ListOfFavoriteProductsUI(viewModel: FavoriteProductViewModel) {
    viewModel.getProducts()
    //val uiState = viewModel.favoriteProducts.collectAsStateWithLifecycle()
    val uiState by viewModel.favoriteProducts.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { contentPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            /*when(uiState.value) {
                is Response.Loading -> { LoadingIndicator() }
                is Response.Success -> {
                    LazyColumn {
                        items((uiState.value as Response.Success).data.size) {
                            FavoriteProductUI((uiState.value as Response.Success).data[it]) {
                                viewModel.deleteProductFromFavorite((uiState.value as Response.Success).data[it])
                            }
                        }
                    }
                }
                is Response.Failure -> {
                    Text(
                        text = "Sorry, we can't show products now.",
                        fontSize = 22.sp,
                        modifier = Modifier.fillMaxSize().wrapContentSize()
                    )
                }
            }*/
            when(uiState) {
                is Response.Loading -> { LoadingIndicator() }
                is Response.Success -> {
                    LazyColumn {
                        items((uiState as Response.Success).data.size) {
                            FavoriteProductUI((uiState as Response.Success).data[it]) {
                                viewModel.deleteProductFromFavorite((uiState as Response.Success).data[it])
                            }
                        }
                    }
                }
                is Response.Failure -> {
                    Text(
                        text = "Sorry, we can't show products now.",
                        fontSize = 22.sp,
                        modifier = Modifier.fillMaxSize().wrapContentSize()
                    )
                }
            }
            LaunchedEffect(Unit) {
                viewModel.message.collect { message ->
                    snackBarHostState.showSnackbar(
                        message = message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
    ) {
        CircularProgressIndicator()
    }
}
