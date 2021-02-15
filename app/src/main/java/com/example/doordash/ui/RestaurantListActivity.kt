package com.example.doordash.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doordash.R
import com.example.doordash.data.LocationProvider
import com.example.doordash.data.RestaurantRepository
import com.example.doordash.network.RestaurantApiFactory
import kotlinx.android.synthetic.main.activity_main.*

class RestaurantListActivity : AppCompatActivity() {

    private val repository by lazy {
        val api = RestaurantApiFactory.create()
        RestaurantRepository(api)
    }

    private val locationProvider by lazy {
        LocationProvider()
    }

    private val restaurantAdapter by lazy {
        RestaurantAdapter()
    }

    private lateinit var viewModel: RestaurantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.tool_bar))

        // Delegate UI presentation logic to a ViewModel
        val factory = RestaurantViewModelFactory(repository, locationProvider)
        viewModel = ViewModelProvider(this, factory).get(RestaurantViewModel::class.java)

        // Use LiveData for a more reactive architecture
        viewModel.getRestaurants().observe(this, Observer { restaurants ->
            restaurantAdapter.setRestaurants(restaurants)
        })

        setupUI()
        loadRestaurants()
    }

    private fun setupUI() {
        // Setup SwipeRefreshLayout
        swipeRefresh.setOnRefreshListener {
            loadRestaurants()
            swipeRefresh.isRefreshing = false
        }

        // Setup RecyclerView
        restaurauntList.adapter = restaurantAdapter
        restaurauntList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        restaurauntList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        restaurauntList.setHasFixedSize(true)
    }

    private fun loadRestaurants() {
        viewModel.loadRestaurants()
    }
}