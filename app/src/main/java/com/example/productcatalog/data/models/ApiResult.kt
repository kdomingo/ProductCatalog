package com.example.productcatalog.data.models

data class ApiResult<out T>(
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val responseCode: String? = null,
    val data: T
)
