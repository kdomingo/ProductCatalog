package com.example.productcatalog.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Metadata(
    val createdAt: String = "",
    val updatedAt: String = "",
    val barcode: String = "",
    val qrCode: String = ""
)
