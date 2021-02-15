package com.example.doordash.data

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.doordash.model.Restaurant
import com.example.doordash.network.RestaurantApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "RestaurantRepository"

class RestaurantRepository(private val restaurantApi: RestaurantApi) {

    companion object {
        const val OFFSET = 0
        const val LIMIT = 50
    }

    // public for testing, Mockito cannot stub this call if private
    val restaurants: MutableLiveData<List<Restaurant>> by lazy {
        MutableLiveData<List<Restaurant>>()
    }

    fun getRestaurants(): LiveData<List<Restaurant>> {
        return restaurants
    }

    private val apiCallback = object : Callback<List<Restaurant>> {
        override fun onFailure(call: Call<List<Restaurant>>?, t:Throwable?) {
            Log.e(TAG, "Problem calling API {${t?.message}}")
        }

        override fun onResponse(call: Call<List<Restaurant>>?, response: Response<List<Restaurant>>?) {
            response?.let { response ->
                if (!response.isSuccessful) {
                    Log.e(TAG, "Request was unsuccessful")
                    return
                }
                restaurants.value = response.body()
            }
        }
    }

    fun loadRestaurants(location: Location) {
        val call = restaurantApi.getRestaurants(location.latitude, location.longitude, OFFSET, LIMIT)
        call.enqueue(apiCallback)
    }

    /*
     * TESTING ONLY. DO NOT CALL FROM APPLICATION CODE
     */

    fun testing_setRestaurants(newRestaurants: List<Restaurant>?) {
        restaurants.value = newRestaurants
    }

}