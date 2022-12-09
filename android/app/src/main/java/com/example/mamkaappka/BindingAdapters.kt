package com.example.mamkaappka

import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mamkaappka.network.DayPhoto

/**
 * Updates the data shown in the [RecyclerView]
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<DayPhoto>?) {
    val adapter = recyclerView.adapter as DaysPhotosAdapter
    adapter.submitList(data)
}


/**
 * Uses the Coil library to load an image by URL into an [ImageView]
 */

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Log.d("MAMKAPP"," Fetching  http://rh.xf.cz/api/uploads/${imgUrl}")
        val imgUri = ("http://rh.xf.cz/api/uploads/${imgUrl}").toUri().buildUpon().scheme("http").build()
        imgView.load(imgUri) {
            placeholder(com.google.android.material.R.drawable.ic_clock_black_24dp)
            error(com.google.android.material.R.drawable.mtrl_ic_error)
        }
    }
}
