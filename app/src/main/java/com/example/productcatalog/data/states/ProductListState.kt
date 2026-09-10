package com.example.productcatalog.data.states

import com.example.productcatalog.data.models.Product

data class ProductListState(
    val products: List<Product> = emptyList(),
    val query: String? = null,
    val error: Boolean = false,
    val loading: Boolean = false,
)
