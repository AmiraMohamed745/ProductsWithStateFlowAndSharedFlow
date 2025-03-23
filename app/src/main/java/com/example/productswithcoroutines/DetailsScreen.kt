package com.example.productswithcoroutines

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.productswithcoroutines.ui.theme.ProductsWithCoroutinesTheme

class DetailsScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val product = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra<Product>(Constants.PRODUCT_SELECTED, Product::class.java)
        } else {
            intent.getParcelableExtra<Product>(Constants.PRODUCT_SELECTED)
        }

        setContent {
            ProductsWithCoroutinesTheme {
                if (product != null) {
                    DetailsScreen(product)
                }
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun DetailsScreen(product: Product) {
    Column(modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)) {
        GlideImage(
            model = product.thumbnail,
            contentDescription = "Image of ${product.title}"
        )
        Text(
            text = product.title,
            fontSize = 14.sp
        )
        Text(
            text = product.description,
            fontSize = 14.sp
        )
        Text(
            text = product.category,
            fontSize = 14.sp
        )
        Text(
            text = product.brand ?: "",
            fontSize = 14.sp
        )
        Text(
            text = product.price.toString(),
            fontSize = 14.sp
        )
    }
}