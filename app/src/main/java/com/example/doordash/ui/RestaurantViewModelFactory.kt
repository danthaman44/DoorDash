package com.example.doordash.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.doordash.data.LocationProvider
import com.example.doordash.data.RestaurantRepository

class RestaurantViewModelFactory(
    private val repository: RestaurantRepository,
    private val locationProvider: LocationProvider) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestaurantViewModel::class.java)) {
            return RestaurantViewModel(repository, locationProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}