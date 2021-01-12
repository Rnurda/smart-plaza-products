package com.example.productssearch.api

import com.example.productssearch.data.SmartPlazaProduct

data class SmartPlazaResponse(
    val productResponses: List<SmartPlazaProduct>
)