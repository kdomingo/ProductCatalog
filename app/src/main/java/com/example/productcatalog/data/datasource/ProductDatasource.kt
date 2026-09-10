package com.example.productcatalog.data.datasource

import com.example.productcatalog.data.models.Product
import com.example.productcatalog.data.models.QueryOptions
import io.ktor.client.HttpClient

interface ProductDatasource {
    suspend fun fetchAll(options: QueryOptions): List<Product>
    suspend fun fetch(id: String): Product
}

