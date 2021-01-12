package com.example.productssearch.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.productssearch.api.SmartPlazaApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SmartPlazaRepository @Inject constructor(private val smartPlazaApi: SmartPlazaApi) {

    fun getSearchResults(query: String, sortName: String, lowCost: Int, highCost: Int) =
        Pager(
            config = PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SmartPlazaPagingSource(
                    smartPlazaApi = smartPlazaApi,
                    query = query,
                    sortName = sortName,
                    highCost = highCost,
                    lowCost = lowCost
                )
            }
        ).liveData
}