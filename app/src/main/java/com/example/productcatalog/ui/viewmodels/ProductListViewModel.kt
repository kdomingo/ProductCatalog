package com.example.productcatalog.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productcatalog.data.services.ProductService
import com.example.productcatalog.data.states.ProductListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductListViewModel(private val service: ProductService): ViewModel() {
    private val _state = MutableStateFlow<ProductListState>(ProductListState())
    val state = _state.asStateFlow()

    fun search(query: String) {
        viewModelScope.launch {
            _state.update { current -> current.copy(query = query) }
        }
    }
}