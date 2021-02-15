package com.example.doordash.data

import android.location.Location

class LocationProvider {

    companion object {
        const val LAT = 37.422740
        const val LONG = -122.139956
    }

    val currentLocation by lazy {
        val location = Location("DoordashHQ")
        location.longitude = LONG
        location.latitude = LAT
        location
    }

}