package com.example.productcatalog.data.repository

import com.example.productcatalog.data.models.Product
import com.example.productcatalog.data.models.QueryOptions

interface ProductRepository {
    suspend fun fetchAll(options: QueryOptions): List<Product>
    suspend fun fetch(id: String): Product
}