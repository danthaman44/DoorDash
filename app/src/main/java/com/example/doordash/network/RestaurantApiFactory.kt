package com.example.doordash.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_BASE_URL = "https://api.doordash.com/"

class RestaurantApiFactory {
    companion object {
        fun create(): RestaurantApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return RestaurantApi(retrofit.create(RestaurantService::class.java))
        }
    }
}