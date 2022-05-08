package com.example.foodapp.presention.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.data.model.Meal
import com.example.foodapp.data.model.MealDB
import com.example.foodapp.databinding.SearchItemBinding

class FavoritAdapter : RecyclerView.Adapter<FavoritAdapter.FavoritHolder>() {
    private val callBack = object : DiffUtil.ItemCallback<MealDB>() {
        override fun areItemsTheSame(oldItem: MealDB, newItem: MealDB): Boolean {
            return oldItem.mealId == newItem.mealId
        }

        override fun areContentsTheSame(oldItem: MealDB, newItem: MealDB): Boolean {
            return oldItem == newItem
        }
    }
    var differ = AsyncListDiffer(this, callBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritHolder {
        return FavoritHolder(
            SearchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoritHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class FavoritHolder(private val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meals: MealDB) {
            binding.apply {
                root.setOnClickListener {
                    OnItemClickListener?.let {
                        it(meals)
                    }
                }
                textMealName.text = meals.mealName
                Glide.with(imageSearch.context)
                    .load(meals.mealThumb)
                    .into(imageSearch)
            }

        }
    }

    private var OnItemClickListener: ((MealDB) -> Unit)? = null
    fun setItemClickListner(listner: (MealDB) -> Unit) {
        OnItemClickListener = listner
    }
}