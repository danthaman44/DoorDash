package com.example.doordash.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.doordash.R
import com.example.doordash.model.Restaurant
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.restaurant_item_view.view.*

class RestaurantAdapter: RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    private var restaurantList = listOf<Restaurant>()

    fun setRestaurants(restaurants: List<Restaurant>) {
        restaurantList = restaurants
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_item_view, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(restaurantList[position])
    }

    override fun onViewRecycled(holder: RestaurantViewHolder) {
        super.onViewRecycled(holder)
        // clear image and cancel request to prevent showing images in recycled ViewHolder
        holder.itemView.imageView.setImageDrawable(null)
        Picasso.get().cancelRequest(holder.itemView.imageView)
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    class RestaurantViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bind(restaurant: Restaurant) {
            itemView.nameText.text = restaurant.name
            itemView.descriptionText.text = restaurant.description
            itemView.statusText.text = restaurant.status
            if (restaurant.cover_img_url.isNotBlank()) {
                Picasso.get().load(restaurant.cover_img_url).into(itemView.imageView)
            }
        }
    }
}