package com.example.foodapp.presention.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.data.model.Meal
import com.example.foodapp.data.model.MealDetail
import com.example.foodapp.databinding.SearchItemBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchHolder>() {
    private val callBack = object : DiffUtil.ItemCallback<MealDetail>() {
        override fun areItemsTheSame(oldItem: MealDetail, newItem: MealDetail): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealDetail, newItem: MealDetail): Boolean {
            return oldItem == newItem
        }
    }
    var differ = AsyncListDiffer(this, callBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        var view = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchHolder(view)

    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class SearchHolder(private val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mealDetail: MealDetail) {
            binding.apply {
                textMealName.text = mealDetail.strMeal
                Glide.with(imageSearch.context)
                    .load(mealDetail.strMealThumb)
                    .into(imageSearch)
                root.setOnClickListener {
                    OnItemClickListener?.let {
                        it(mealDetail)
                    }
                }
            }

        }

    }

    private var OnItemClickListener:((MealDetail)->Unit)?= null

    fun setItemClickListener(listener: (MealDetail) -> Unit) {
        OnItemClickListener = listener
    }


}