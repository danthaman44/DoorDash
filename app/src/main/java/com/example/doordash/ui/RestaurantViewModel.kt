package com.example.doordash.ui

import androidx.lifecycle.*
import com.example.doordash.data.LocationProvider
import com.example.doordash.data.RestaurantRepository
import com.example.doordash.model.Restaurant

// Dependency-inject the repository and locationProvider so they can be mocked in tests
class RestaurantViewModel(
    private val repository: RestaurantRepository,
    private val locationProvider: LocationProvider) : ViewModel() {

    private val restaurants: MutableLiveData<List<Restaurant>> by lazy {
        MutableLiveData<List<Restaurant>>()
    }

    private val restaurantObserver = Observer<List<Restaurant>> { newRestaurants ->
        restaurants.value = newRestaurants
    }

    init {
        // Register observer on the repository data
        repository.getRestaurants().observeForever(restaurantObserver)
    }

    fun getRestaurants(): LiveData<List<Restaurant>> {
        return restaurants
    }

    fun loadRestaurants() {
        repository.loadRestaurants(locationProvider.currentLocation)
    }

    override fun onCleared() {
        // Unregister observer to avoid memory leak
        repository.getRestaurants().removeObserver(restaurantObserver)
        super.onCleared()
    }

    /*
     * TESTING ONLY. DO NOT CALL FROM APPLICATION CODE
     */

    fun testing_getRepository(): RestaurantRepository {
        return repository
    }

    fun testing_getLocationProvider(): LocationProvider {
        return locationProvider
    }

    fun testing_setRestaurants(newRestaurants: List<Restaurant>?) {
        restaurants.value = newRestaurants
    }

}