package com.example.productcatalog.data.datasource

import com.example.productcatalog.data.models.Product
import com.example.productcatalog.data.models.QueryOptions
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.json.Json

class ProductDatasourceImpl(
    private val client: HttpClient
) : ProductDatasource {

    override suspend fun fetchAll(options: QueryOptions): List<Product> {
        val query = Json.encodeToString(options)
        return client.get("https://dummyjson.com/products?$query").body()
    }

    override suspend fun fetch(id: String): Product {
        return client.get {  }
    }
}