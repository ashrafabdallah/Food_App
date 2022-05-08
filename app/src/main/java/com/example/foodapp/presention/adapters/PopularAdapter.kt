package com.example.foodapp.presention.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.data.model.Meal
import com.example.foodapp.databinding.MostPopularCardBinding

class PopularAdapter : RecyclerView.Adapter<PopularAdapter.PopularHolder>() {

    private val callBack = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, callBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularHolder {
        val binding =
            MostPopularCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class PopularHolder(private val binding: MostPopularCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(meal: Meal) {
            Glide.with(binding.imgPopularMeal.context)
                .load(meal.strMealThumb)
                .into(binding.imgPopularMeal)
            binding.root.setOnClickListener {
                OnItemClickListener?.let {
                    it(meal)
                }
            }
        }


    }

    private var OnItemClickListener: ((Meal) -> Unit)? = null
    fun setItemClickListner(listner: (Meal) -> Unit) {
        OnItemClickListener = listner
    }

}