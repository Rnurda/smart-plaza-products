package com.example.productssearch.data

data class filterModel(
    val query:String,
    val sortName: String,
    val highCost: Int,
    val lowCost: Int
)