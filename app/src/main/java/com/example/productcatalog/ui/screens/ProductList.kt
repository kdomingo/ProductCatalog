package com.example.productcatalog.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.productcatalog.data.models.Product
import com.example.productcatalog.ui.viewmodels.ProductListViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import org.koin.androidx.compose.koinViewModel
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductListViewModel = koinViewModel()
) {

    val searchFieldState = rememberTextFieldState()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(searchFieldState) {
        snapshotFlow { searchFieldState.text }
            .debounce(4000.milliseconds)
            .collect { text -> viewModel.search(text.toString()) }
    }

    Scaffold(modifier = modifier) {
        Column(modifier = Modifier.padding(it)) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                state = searchFieldState,
                placeholder = {
                    Text("Search product")
                },
                trailingIcon = {
                    if (searchFieldState.text.isNotEmpty()) {
                        IconButton(onClick = {
                            searchFieldState.clearText()
                        }) {
                            Icon(Icons.Outlined.Clear, contentDescription = "clear button")
                        }
                    }
                })

            if (state.loading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

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
    if (products.isNotEmpty()) {
        LazyColumn(modifier = modifier) {
            items(items = products, key = { item -> item.id }) { product ->
                ProductItemRow(product = product, onProductClicked = onProductClicked)
                HorizontalDivider()
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("No Products Found")
        }
    }
}

@Composable
fun ProductTag(tag: String) {
    Card(colors = CardDefaults.cardColors(containerColor = Color.LightGray)) {
        Text(
            tag,
            style = TextStyle(fontSize = 12.sp),
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun ProductItemRow(
    product: Product? = null,
    onProductClicked: (Product) -> Unit = {}
) {

    val thumbnailPlaceholder = rememberVectorPainter(Icons.Filled.ShoppingCart)

    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clickable(enabled = null != product) {
                onProductClicked(product!!)
            },
        shape = RoundedCornerShape(size = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        Row {
            product?.thumbnail?.takeIf { thumbnail -> thumbnail.isNotEmpty() }?.let { thumbnail ->
                AsyncImage(
                    model = thumbnail,
                    contentDescription = "product thumbnail",
                    placeholder = thumbnailPlaceholder,
                    modifier = Modifier.weight(0.3f)
                )
            } ?: Icon(Icons.Filled.ShoppingCart, contentDescription = "fallback product icon")

            Column(
                modifier = Modifier
                    .padding(4.dp).weight(0.7f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(product?.title ?: "Unknown")
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(Icons.Filled.Star, contentDescription = "ratings star")
                    Text("%.1f".format(product?.rating ?: 0))
                }

                product?.tags?.takeIf { tags -> tags.isNotEmpty() }?.let { tags ->
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        items(tags, key = { tag -> tag }) { tag ->
                            ProductTag(tag.uppercase())
                        }
                    }
                }

            }
        }
    }
}

@Preview
@Composable
private fun ProductListPreview() {
    ProductList(
        products = listOf(
            Product(
                id = 1,
                title = "Wonderful skin cream",
                tags = listOf("Beauty", "Essence")
            )
        )
    )
}