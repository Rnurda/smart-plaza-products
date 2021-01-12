package com.example.productssearch.di

import com.example.productssearch.api.SmartPlazaApi
import com.example.productssearch.api.SmartPlazaResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit =
        Retrofit.Builder()
            .baseUrl(SmartPlazaApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideSmartPlazaApi(retrofit: Retrofit): SmartPlazaApi =
        retrofit.create(SmartPlazaApi::class.java)

}