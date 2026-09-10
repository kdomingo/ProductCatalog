package com.example.productcatalog.data.repository

import com.example.productcatalog.data.datasource.ProductDatasource
import com.example.productcatalog.data.models.Product
import com.example.productcatalog.data.models.QueryOptions

class ProductRepositoryImpl(private val datasource: ProductDatasource): ProductRepository {

    override suspend fun fetchAll(options: QueryOptions): List<Product> {
        return datasource.fetchAll(options)
    }

    override suspend fun fetch(id: String): Product {
        return datasource.fetch(id)
    }
}