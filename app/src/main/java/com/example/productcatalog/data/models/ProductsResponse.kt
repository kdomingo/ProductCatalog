package com.example.productcatalog.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ProductsResponse(
    val products: List<Product> = emptyList()
)
