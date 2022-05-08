package com.example.foodapp.presention.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.data.model.Category
import com.example.foodapp.data.model.Meal
import com.example.foodapp.databinding.CategoryCardBinding

class HomeCategoryAdapter : RecyclerView.Adapter<HomeCategoryAdapter.HomeCategoryAdapterHolder>() {

    private val callBack = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
    var differ = AsyncListDiffer(this, callBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoryAdapterHolder {
        var v = CategoryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeCategoryAdapterHolder(v)
    }

    override fun onBindViewHolder(holder: HomeCategoryAdapterHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class HomeCategoryAdapterHolder(private var binding: CategoryCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.tvCategoryName.text = category.strCategory
            Glide.with(binding.imgCategory.context)
                .load(category.strCategoryThumb)
                .into(binding.imgCategory)
            binding.root.setOnClickListener {
                OnItemClickListener?.let {
                    it(category)
                }
            }
        }

    }
    private var OnItemClickListener:((Category)->Unit)?=null
    fun setOnItemClickListner(listner:(Category)->Unit){
        OnItemClickListener=listner
    }
}