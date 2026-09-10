package com.example.productcatalog.data.di

import com.example.productcatalog.data.client.KtorClient
import com.example.productcatalog.data.datasource.ProductDatasource
import com.example.productcatalog.data.datasource.ProductDatasourceImpl
import com.example.productcatalog.data.repository.ProductRepository
import com.example.productcatalog.data.repository.ProductRepositoryImpl
import com.example.productcatalog.data.services.ProductService
import com.example.productcatalog.data.services.ProductServiceImpl
import com.example.productcatalog.ui.viewmodels.ProductListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ProductDatasource> { ProductDatasourceImpl(KtorClient.client()) }
    single<ProductRepository> { ProductRepositoryImpl(get()) }
    single<ProductService> { ProductServiceImpl(get()) }
    viewModel { ProductListViewModel(get()) }
}