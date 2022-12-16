package com.example.mamkaappka

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mamkaappka.databinding.OneDayPhotoBinding
import com.example.mamkaappka.network.DayPhoto
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DaysPhotosAdapter (val clickListener: DaysPhotosListener) :
    ListAdapter<DayPhoto, DaysPhotosAdapter.DaysPhotosViewHolder>(DiffCallback){

    // Viewholder will allow to access views created from layout file in code
    class DaysPhotosViewHolder(private var binding: OneDayPhotoBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(clickListener: DaysPhotosListener, dayphoto: DayPhoto){
            binding.dayPhoto = dayphoto             // this won't work without the <data> tag in fragment_book.xaml
            binding.clickListener = clickListener   // without this, the click listener below and defined in fragment_book.xaml won't work

            // make nicer date
            binding.dayphotoText.text = LocalDate.parse(dayphoto.date).format(DateTimeFormatter.ofPattern("d. MMMM yy"))

            binding.executePendingBindings()        // this is to execute the bindings..
        }
    }

    // inflate the layout....
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysPhotosViewHolder {
        val viewHolder = DaysPhotosViewHolder(
            OneDayPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        return viewHolder
    }


    //override the onBindViewHolder() to bind the view at the specified position
    override fun onBindViewHolder(holder: DaysPhotosViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }

    // lets try to setup a onclick listener first...
    class DaysPhotosListener (val clickListener: (dayphoto: DayPhoto) -> Unit) {
        fun onClick(dayphoto: DayPhoto) = clickListener(dayphoto)
    }

    /*
    DiffCallBack is an object that helps the ListAdapter determine which items in the new
    and old lists are different when updating the list.
    */
    companion object{
        private val DiffCallback = object : DiffUtil.ItemCallback<DayPhoto>(){

            override fun areItemsTheSame(oldItem: DayPhoto, newItem: DayPhoto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DayPhoto, newItem: DayPhoto): Boolean {
                return oldItem == newItem
            }
        }
    }
}