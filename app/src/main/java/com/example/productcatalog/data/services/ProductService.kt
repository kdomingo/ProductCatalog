package com.example.productcatalog.data.services

import com.example.productcatalog.data.models.ApiResult
import com.example.productcatalog.data.models.Product
import com.example.productcatalog.data.models.QueryOptions

interface ProductService {
    suspend fun fetchAll(options: QueryOptions): ApiResult<List<Product>?>
    suspend fun fetch(id: String): ApiResult<Product?>
}