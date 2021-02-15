package com.example.doordash.network

import com.example.doordash.model.Restaurant
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantService {
    @GET("v2/restaurant/")
    fun getRestaurants(@Query("lat") latitude: Double,
                       @Query("lng") longitude: Double,
                       @Query("offset") offset: Int = 0,
                       @Query("limit") limit: Int = 50): Call<List<Restaurant>>
}