package com.example.productssearch.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SmartPlazaProduct(
    val id: Int,
    val name: String?,
    val shortDescription: String?,
    val section: ProductsSection?,
    val category: ProductsCategory?,
    val superCategory: SuperCategory?,
    val price: Int?,
    val promoPrice: Int?,
    val photo_1: String
) : Parcelable {

    @Parcelize
    data class ProductsCategory(
        val id: Int?,
        val name: String?
    ) : Parcelable

    @Parcelize
    data class ProductsSection(
        val id: Int?,
        val name: String?
    ) : Parcelable

    @Parcelize
    data class SuperCategory(
        val id: Int?,
        val name: String?
    ) : Parcelable

}