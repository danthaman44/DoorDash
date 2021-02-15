package com.example.doordash

import android.os.Build
import androidx.lifecycle.LiveData
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.doordash.data.RestaurantRepository
import com.example.doordash.model.Restaurant
import com.example.doordash.network.RestaurantApi
import junit.framework.Assert.*
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class RestaurantRepositoryTest {

    @Mock
    private lateinit var observer: Observer<List<Restaurant>>

    @Mock
    private lateinit var repository: RestaurantRepository

    @Mock
    private lateinit var api: RestaurantApi

    @Mock
    lateinit var restaurants: LiveData<List<Restaurant>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = RestaurantRepository(api)
    }

    @Test
    fun test_repository_setup() {
        assertNull(repository.getRestaurants().value)
    }

    @Test
    fun test_repository_observer() {
        repository.getRestaurants().observeForever(observer)
        assertTrue(repository.getRestaurants().hasObservers())

        repository.testing_setRestaurants(restaurants.value)
        verify(observer).onChanged(restaurants.value)

        repository.getRestaurants().removeObserver(observer)
        assertFalse(repository.getRestaurants().hasObservers())
    }

    @Test
    fun test_repository_data() {
        assertNull(repository.getRestaurants().value)

        val realRestaurants = mutableListOf<Restaurant>()
        realRestaurants.add(Restaurant("", "", "", 0, ""))
        repository.testing_setRestaurants(realRestaurants)

        assertNotNull(repository.getRestaurants().value)
        assertEquals(repository.getRestaurants().value?.size, 1)
    }

}