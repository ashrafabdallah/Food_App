package com.example.foodapp.presention.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentHomeCategoryBinding
import com.example.foodapp.presention.activity.MainActivity
import com.example.foodapp.presention.adapters.CategoryItemAdapter
import com.example.foodapp.presention.viewmodel.home.HomeViewModel
import com.example.foodapp.util.Resource


class HomeCategoryFragment : Fragment() {

    lateinit var binding: FragmentHomeCategoryBinding
    lateinit var homeViewModel: HomeViewModel
    lateinit var categoryItemAdapter: CategoryItemAdapter
    lateinit var catergoryCount: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeCategoryBinding.bind(view)
        val args:HomeCategoryFragmentArgs by navArgs()
        var data = args.selectedCategoryItem
        catergoryCount = data.strCategory
        binding.CategoryProgressBar.visibility = View.VISIBLE
        homeViewModel = (activity as MainActivity).homeViewModel
        categoryItemAdapter = (activity as MainActivity).CategoryItemAdapter
        intRecyclerView()
        onClickRecyclerViewItem()
        homeViewModel.getConnect()
        homeViewModel.connectData.observe(viewLifecycleOwner, Observer {
            if (it == "ok") {
                binding.CategoryProgressBar.visibility = View.GONE
                getCategoryFromResponse(data.strCategory)
            } else {
                binding.CategoryProgressBar.visibility = View.VISIBLE
                Toast.makeText(context, "No Internet.........", Toast.LENGTH_LONG).show()

            }
        })


    }

    private fun onClickRecyclerViewItem() {
        categoryItemAdapter.setItemClickListener {
            var bundle = Bundle().apply {
                Log.i("DA1",it.idMeal)
                putString("meal_name",it.idMeal)
            }
            findNavController().navigate(R.id.mealDetailsFragment,bundle)
        }
    }

    private fun intRecyclerView() {
        binding.mealRecyclerview.apply {
            adapter = categoryItemAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

        }
    }

    private fun getCategoryFromResponse(strCategory: String) {
        homeViewModel.getPopularMeal(strCategory)
        homeViewModel.popularMeal.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    binding.CategoryProgressBar.visibility = View.GONE
                    response.data?.let {

                        if (it.meals == null) {
                            it.meals = emptyList()
                            Toast.makeText(context, "No Data.....", Toast.LENGTH_LONG).show()
                            categoryItemAdapter.notifyDataSetChanged()
                            findNavController().navigate(R.id.homefragment)
                        } else {
                            categoryItemAdapter.differ.submitList(it.meals)
                            Log.i("SIZE", it.meals.size.toString())
                            var count = catergoryCount + " : " + it.meals.size.toString()
                            /// catergoryCount.plus( it.meals.size.toString())
                            binding.tvCategoryCount.text = count
                        }

                        //  findNavController().popBackStack()


                    }
                }
                is Resource.Error -> {
                    binding.CategoryProgressBar.visibility = View.GONE
                    response.message?.let {
                        Log.i("IN", "NO POPULAR DATA")
                        Toast.makeText(activity, "An error occurred : $it", Toast.LENGTH_LONG)
                            .show()
                    }

                }
                is Resource.Loading -> {
                    binding.CategoryProgressBar.visibility = View.VISIBLE

                }
            }

        })
    }
}