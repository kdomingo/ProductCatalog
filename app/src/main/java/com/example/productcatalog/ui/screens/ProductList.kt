package com.example.productcatalog.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.productcatalog.data.models.Product
import com.example.productcatalog.ui.viewmodels.ProductListViewModel

@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductListViewModel = viewModel()
) {

    val searchFieldState = rememberTextFieldState()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(searchFieldState) {
        snapshotFlow { searchFieldState.text }
            .collect { text -> viewModel.search(text.toString()) }
    }

    Scaffold(modifier = modifier) {
        Column(modifier = Modifier.padding(it)) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                state = searchFieldState,
                trailingIcon = {
                    if (searchFieldState.text.isNotEmpty()) {
                        IconButton(onClick = {
                            searchFieldState.clearText()
                        }) {
                            Icon(Icons.Outlined.Clear, contentDescription = "clear button")
                        }
                    }
                })

            ProductList(products = state.products)
        }
    }
}

@Composable
private fun ProductList(
    modifier: Modifier = Modifier,
    products: List<Product>,
    onProductClicked: (Product) -> Unit = {}
) {
    LazyColumn(modifier = modifier) {
        items(items = products, key = { item -> item.id }) { product ->
            ProductItemRow(product = product, onProductClicked = onProductClicked)
        }
    }
}

@Composable
fun ProductTag(tag: String) {
    Card(colors = CardDefaults.cardColors(containerColor = Color.LightGray)) {
        Text(tag, style = TextStyle(fontSize = 12.sp), modifier = Modifier.padding(4.dp))
    }
}

@Composable
private fun ProductItemRow(
    product: Product? = null,
    onProductClicked: (Product) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .height(150.dp)
            .clickable(enabled = null != product) {
                onProductClicked(product!!)
            },
        shape = RoundedCornerShape(size = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (null != product && product.thumbnail.isNotEmpty()) {
                AsyncImage(
                    model = product.thumbnail,
                    contentDescription = "product thumbnail",
                    modifier = Modifier.fillMaxHeight(0.5f)
                )
            }
            Text(product?.title ?: "Unknown")
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(Icons.Filled.Star, contentDescription = "ratings star")
                Text("${product?.rating ?: 0}")
            }

            LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                items(product?.tags ?: emptyList(), key = { tag -> tag}) { tag ->
                    ProductTag(tag)
                }
            }

        }
    }
}

@Preview
@Composable
private fun ProductListPreview() {
    ProductList(products = listOf(Product(id = 1, title = "Wonderful skin cream", tags = listOf("Beauty", "Essence"))))
}