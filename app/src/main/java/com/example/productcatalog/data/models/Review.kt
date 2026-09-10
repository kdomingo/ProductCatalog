package com.example.productcatalog.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Review(
    val rating: Int = 0,
    val comment: String = "",
    val date: String = "",
    val reviewerName: String = "",
    val reviewerEmail: String = ""
)
