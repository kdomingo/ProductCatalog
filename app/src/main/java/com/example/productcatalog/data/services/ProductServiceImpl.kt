package com.example.productcatalog.data.services

import com.example.productcatalog.data.models.ApiResult
import com.example.productcatalog.data.models.Product
import com.example.productcatalog.data.models.QueryOptions
import com.example.productcatalog.data.repository.ProductRepository

class ProductServiceImpl(private val repository: ProductRepository) : ProductService {

    override suspend fun fetchAll(options: QueryOptions): ApiResult<List<Product>?> {
        val result = repository.fetchAll(options)
        return ApiResult(data = result)
    }

    override suspend fun fetch(id: String): ApiResult<Product?> {
        return ApiResult(data = repository.fetch(id))
    }
}