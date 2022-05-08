package com.example.foodapp.presention.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.data.model.Meal
import com.example.foodapp.databinding.CategoryCardBinding

class CategoryItemAdapter : RecyclerView.Adapter<CategoryItemAdapter.CategoryItemViewHolder>() {
    private val callBacke = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }
    var differ = AsyncListDiffer(this, callBacke)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val view = CategoryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return if (differ.currentList != null) differ.currentList.size else 0
    }

    inner class CategoryItemViewHolder(private val binding: CategoryCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            binding.apply {
                Glide.with(imgCategory.context)
                    .load(meal.strMealThumb)
                    .into(imgCategory)
                tvCategoryName.text = meal.strMeal
                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(meal)
                    }
                }
            }

        }
    }

    private var onItemClickListener: ((Meal) -> Unit)? = null
    fun setItemClickListener(listener: (Meal) -> Unit) {
        onItemClickListener = listener
    }


}