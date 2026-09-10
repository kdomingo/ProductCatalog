package com.example.productcatalog.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productcatalog.data.models.QueryOptions
import com.example.productcatalog.data.services.ProductService
import com.example.productcatalog.data.states.ProductListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductListViewModel(private val service: ProductService): ViewModel() {
    private val _state = MutableStateFlow(ProductListState())
    val state = _state.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        _state.update { current -> current.copy(loading = true) }
        viewModelScope.launch {
            val result = service.fetchAll(QueryOptions())
            _state.update { current -> current.copy(products = result.data ?: emptyList(), error = result.isError, loading = false) }
        }
    }

    fun search(query: String) {
        _state.update { current -> current.copy(loading = true) }
        viewModelScope.launch {
            val options = QueryOptions(query = query)
            val result = service.fetchAll(options)
            _state.update { current -> current.copy(products = result.data ?: emptyList(), error = result.isError, loading = false) }
        }
    }
}