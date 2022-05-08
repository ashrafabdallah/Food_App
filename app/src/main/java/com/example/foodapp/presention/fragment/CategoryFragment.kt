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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMainBinding
import com.example.foodapp.databinding.FragmentCategoryBinding
import com.example.foodapp.presention.activity.MainActivity
import com.example.foodapp.presention.adapters.HomeCategoryAdapter
import com.example.foodapp.presention.viewmodel.home.HomeViewModel
import com.example.foodapp.util.Resource


class CategoryFragment : Fragment() {

    lateinit var binding: FragmentCategoryBinding
    lateinit var homeViewModel: HomeViewModel
    lateinit var homeCategoryAdapter: HomeCategoryAdapter
    lateinit var mainBinding: ActivityMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoryBinding.bind(view)
        homeViewModel = (activity as MainActivity).homeViewModel
        homeCategoryAdapter = (activity as MainActivity).homeCategoryAdapter
        mainBinding = (activity as MainActivity).binding

        initRecyclerView()
        homeViewModel.getConnect()
        homeViewModel.connectData.observe(viewLifecycleOwner, Observer {
            if (it == "ok") {
                getData()
            } else {
                Toast.makeText(context, "No Internet.....", Toast.LENGTH_LONG).show()
            }
        })
        onClickCategoryRecyclerViewItem()

    }

    private fun initRecyclerView() {
        binding.CategoryRecycler.apply {
            adapter = homeCategoryAdapter
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun getData() {
        showProgressBar()
        homeViewModel.getCategory()
        homeViewModel.category.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        homeCategoryAdapter.differ.submitList(it.categories)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(context, "${it}", Toast.LENGTH_LONG).show()

                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

    }

    fun showProgressBar() {
        binding.progressBarCategory.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        binding.progressBarCategory.visibility = View.GONE

    }
    private fun onClickCategoryRecyclerViewItem() {
        homeCategoryAdapter.setOnItemClickListner {
            var bundle = Bundle().apply {
                putString("selected_item_category", it.strCategory)
            }
            Log.i("CATITEM",it.strCategory)

        findNavController().navigate(R.id.categoryDetailsFragment, bundle)
            mainBinding.BottomNavigationView.visibility = View.GONE
        }
    }
}