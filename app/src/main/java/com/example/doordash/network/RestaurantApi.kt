package com.example.doordash.network

import com.example.doordash.model.Restaurant
import retrofit2.Call

class RestaurantApi(private val restaurantService: RestaurantService) {

    fun getRestaurants(latitude: Double, longitude: Double, offset: Int, limit: Int): Call<List<Restaurant>> {
        return restaurantService.getRestaurants(latitude, longitude, offset, limit)
    }

}