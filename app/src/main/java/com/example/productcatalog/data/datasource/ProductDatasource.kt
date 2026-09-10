package com.example.productcatalog.data.datasource

import com.example.productcatalog.data.models.Product
import com.example.productcatalog.data.models.ProductsResponse
import com.example.productcatalog.data.models.QueryOptions

interface ProductDatasource {
    suspend fun fetchAll(options: QueryOptions): ProductsResponse
    suspend fun fetch(id: String): Product
}
