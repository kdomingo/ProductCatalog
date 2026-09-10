package com.example.productcatalog.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val price: Double = 0.0,
    val discountPercentage: Double = 0.0,
    val rating: Double = 0.0,
    val stock: Int = 0,
    val tags: List<String> = emptyList(),
    val brand: String = "",
    val sku: String = "",
    val weight: Int = 0,
    val dimensions: ProductDimension = ProductDimension(),
    val warrantyInformation: String = "",
    val shippingInformation: String = "",
    val availabilityStatus: String = "",
    val reviews: List<Review> = emptyList(),
    val returnPolicy: String = "",
    val minimumOrderQuantity: Int = 0,
    val meta: Metadata = Metadata(),
    val images: List<String> = emptyList(),
    val thumbnail: String = ""
)
