package com.example.productssearch.ui.products

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.LoadState
import androidx.paging.cachedIn
import com.example.productssearch.data.SmartPlazaProduct
import com.example.productssearch.data.SmartPlazaRepository
import com.example.productssearch.data.filterModel

class ProductsViewModel @ViewModelInject constructor(
    private val repository: SmartPlazaRepository,
    @Assisted state: SavedStateHandle
) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULR_QUERY)


    val products = currentQuery.switchMap { queryString ->
        repository.getSearchResults(query = queryString.query, sortName = queryString.sortName, highCost = queryString.highCost, lowCost =  queryString.lowCost).cachedIn(viewModelScope)
    }

    fun searchProducts(filterModel: filterModel) {
        currentQuery.value = filterModel
    }

    companion object{
        private val DEFAULR_QUERY = filterModel("","", ProductsViewModel.HIGH_PRICE, ProductsViewModel.LOW_PRICE)
        const val HIGH_PRICE = 999999999
        const val LOW_PRICE = 0
    }
}