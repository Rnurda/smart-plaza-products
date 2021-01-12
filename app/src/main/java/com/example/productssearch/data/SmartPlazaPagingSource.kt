package com.example.productssearch.data

import androidx.paging.PagingSource
import com.example.productssearch.api.SmartPlazaApi
import retrofit2.HttpException
import java.io.IOException

private const val SMARTPLAZA_STARTING_PAGE_INDEX = 1

class SmartPlazaPagingSource(
    private val smartPlazaApi: SmartPlazaApi,
    private val query: String,
    private val sortName: String,
    private val highCost: Int,
    private val lowCost: Int

) : PagingSource<Int, SmartPlazaProduct>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SmartPlazaProduct> {
        val position = params.key ?: SMARTPLAZA_STARTING_PAGE_INDEX

        return try {
            val response = smartPlazaApi.searchProducts(query,sortName,highCost,lowCost, position, params.loadSize)
            val products = response.productResponses

            LoadResult.Page(
                data = products,
                prevKey = if (position == SMARTPLAZA_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (products.isEmpty()) null else position + 1
            )
        } catch (ex: IOException) {
            LoadResult.Error(ex)
        } catch (ex: HttpException) {
            LoadResult.Error(ex)
        }

    }
}