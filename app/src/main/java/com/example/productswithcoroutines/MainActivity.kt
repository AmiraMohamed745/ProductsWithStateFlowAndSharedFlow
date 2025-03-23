package com.example.productswithcoroutines

import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.productswithcoroutines.db.ProductDatabase
import com.example.productswithcoroutines.db.ProductsLocalDataSource
import com.example.productswithcoroutines.model.ProductsRepositoryImpl
import com.example.productswithcoroutines.network.ProductsRemoteDataSource
import com.example.productswithcoroutines.network.RetrofitHelper
import com.example.productswithcoroutines.ui.theme.ProductsWithCoroutinesTheme
import com.example.productswithcoroutines.viewmodel.AllProductsFactory
import com.example.productswithcoroutines.viewmodel.AllProductsViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductsWithCoroutinesTheme {
                HomeScreenUI()
            }
        }
    }
}

@Composable
fun HomeScreenUI() {
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(100.dp)
    ) {
        Button(onClick = {
            val intent = Intent(context, AllProductsScreen::class.java)
            context.startActivity(intent)
        }) {
            Text("All Products")
        }
        Button(onClick = {
            val intent = Intent(context, FavoriteScreen::class.java)
            context.startActivity(intent)
        }) {
            Text("Favorites")
        }
        Button(onClick = {}) {
            Text("Exit")
        }
    }
}

