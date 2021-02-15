package com.example.doordash

import android.os.Build
import androidx.lifecycle.LiveData
import com.example.doordash.ui.RestaurantViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.doordash.data.LocationProvider
import com.example.doordash.data.RestaurantRepository
import com.example.doordash.model.Restaurant
import junit.framework.Assert.*
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class RestaurantViewModelTest {

    @Mock
    private lateinit var viewModel: RestaurantViewModel

    @Mock
    private lateinit var observer: Observer<List<Restaurant>>

    @Mock
    private lateinit var repository: RestaurantRepository

    @Mock
    private lateinit var locationProvider: LocationProvider

    @Mock
    lateinit var restaurants: LiveData<List<Restaurant>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(repository.getRestaurants())
                .thenAnswer{ return@thenAnswer restaurants }
        viewModel = RestaurantViewModel(repository, locationProvider)
    }

    @Test
    fun test_view_model_setup() {
        assertNull(viewModel.getRestaurants().value)
        assertNotNull(viewModel.testing_getLocationProvider())
        assertNotNull(viewModel.testing_getRepository())
    }

    @Test
    fun test_view_model_observer() {
        viewModel.getRestaurants().observeForever(observer)
        assertTrue(viewModel.getRestaurants().hasObservers())

        viewModel.testing_setRestaurants(restaurants.value)
        verify(observer).onChanged(restaurants.value)

        viewModel.getRestaurants().removeObserver(observer)
        assertFalse(repository.getRestaurants().hasObservers())
    }

    @Test
    fun test_view_model_data() {
        assertNull(viewModel.getRestaurants().value)

        val realRestaurants = mutableListOf<Restaurant>()
        realRestaurants.add(Restaurant("", "", "", 0, ""))
        viewModel.testing_setRestaurants(realRestaurants)

        assertNotNull(viewModel.getRestaurants().value)
        assertEquals(viewModel.getRestaurants().value?.size, 1)
    }
}