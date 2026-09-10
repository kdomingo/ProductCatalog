package com.example.productcatalog.data.datasource

import com.example.productcatalog.data.models.Product
import com.example.productcatalog.data.models.QueryOptions
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ProductDatasourceImpl(
    private val client: HttpClient
) : ProductDatasource {

    override suspend fun fetchAll(options: QueryOptions): List<Product> {
        return client.get {
            options.query.takeIf { it.isNotEmpty() }?.let { query ->
                parameter("q", query)
            }
            parameter("limit", options.limit)
            parameter("skip", options.skip)
        }.body()
    }

    override suspend fun fetch(id: String): Product {
        return client.get{}.body()
    }
}