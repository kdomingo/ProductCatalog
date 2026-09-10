package com.example.productcatalog.data.models

import kotlinx.serialization.Serializable

@Serializable
data class QueryOptions(
    val query: String,
    val limit: Int = 20,
    val skip: Int = 0
)
