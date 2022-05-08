package com.example.foodapp.presention.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.data.model.MealDetail
import com.example.foodapp.databinding.ActivityMainBinding
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.presention.activity.MainActivity
import com.example.foodapp.presention.adapters.HomeCategoryAdapter
import com.example.foodapp.presention.adapters.PopularAdapter
import com.example.foodapp.presention.viewmodel.home.HomeViewModel
import com.example.foodapp.util.Resource


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var homeViewModel: HomeViewModel
    lateinit var popularAdapter: PopularAdapter
    lateinit var homeCategoryAdapter: HomeCategoryAdapter
    lateinit var mainBinding: ActivityMainBinding

    var randomMealId = ""
    lateinit var meal: MealDetail
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        homeViewModel = (activity as MainActivity).homeViewModel
        popularAdapter = (activity as MainActivity).popularAdapter
        homeCategoryAdapter = (activity as MainActivity).homeCategoryAdapter
        mainBinding = (activity as MainActivity).binding

        homeViewModel.getConnect()

        homeViewModel.connectData.observe(viewLifecycleOwner, Observer {
            if (it == "ok") {
                Log.i("PP", "RUNNING")
                showProgressBar()
                showImages()
                ShowPopularMeal()
                ShowCategory()

            } else {
                Log.i("IN", "NO RUNNING")
                hideProgressBar()
                binding.lottieAnimation.visibility = View.VISIBLE
            }
        })
        initRecyclerView()
        onClickRecyclerViewItem()
        onClickCategoryRecyclerViewItem()
        searchView()

    }

    private fun searchView() {

        binding.imgSearch.apply {

            queryHint = "Type Meal Name"
            isSubmitButtonEnabled = true
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    if (query != null) {
                        var bundle = Bundle().apply {
                            putString("querySearch", query)
                        }
                        // mainBinding.BottomNavigationView.visibility=View.GONE
                        findNavController().navigate(R.id.searchragment, bundle)
                    } else {
                        Toast.makeText(context, "Please Enter Mael Name...", Toast.LENGTH_LONG)
                            .show()
                    }

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    private fun onClickCategoryRecyclerViewItem() {
        homeCategoryAdapter.setOnItemClickListner {
            var bundle = Bundle().apply {
                putSerializable("selectedCategory_Item", it)
            }
            mainBinding.BottomNavigationView.visibility = View.GONE
            findNavController().navigate(R.id.homeCategoryFragment, bundle)

        }
    }

    private fun onClickRecyclerViewItem() {
        popularAdapter.setItemClickListner {
            var bundle = Bundle().apply {
                putSerializable("selectedPopular_Item", it)
            }
            findNavController().navigate(R.id.popularItemsFragment, bundle)
            mainBinding.BottomNavigationView.visibility = View.GONE
        }
    }


    private fun showImages() {
        homeViewModel.getRandomImage()
        homeViewModel.imageMutable.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {

                        var d = it.meals.toList()

                        randomMealId = d[0].strCategory.toString()

                        Glide.with(this@HomeFragment)
                            .load(d[0].strMealThumb)
                            .into(binding.imgRandomMeal)

                        meal = it.meals[0]

                        binding.randomMeal.setOnClickListener(View.OnClickListener {
                            val bundle = Bundle().apply {
                                putSerializable("selected_Item", meal)
                            }

                            mainBinding.BottomNavigationView.visibility = View.GONE
                            findNavController().navigate(R.id.mealDetailsFragment, bundle)


                        })

                    }

                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {

                        Toast.makeText(activity, "An error occurred : $it", Toast.LENGTH_LONG)
                            .show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }

            }

        })
    }

    private fun ShowCategory() {
        homeViewModel.getCategory()
        homeViewModel.category.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {


                        Log.i("IN", "Category")
                        homeCategoryAdapter.differ.submitList(it.categories)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {

                        Log.i("IN", "NO Category")
                        Toast.makeText(activity, "An error occurred : $it", Toast.LENGTH_LONG)
                            .show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }

        })
    }

    private fun ShowPopularMeal() {
        homeViewModel.getPopularMeal("beef")
        popularAdapter.notifyDataSetChanged()
        homeViewModel.popularMeal.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        Log.i("IN", "POPULAR DATA")

                        popularAdapter.differ.submitList(it.meals)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Log.i("IN", "NO POPULAR DATA")
                        Toast.makeText(activity, "An error occurred : $it", Toast.LENGTH_LONG)
                            .show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.recViewMealsPopular.apply {
            adapter = popularAdapter
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)


        }
        binding.recyclerViewCategory.apply {
            adapter = homeCategoryAdapter
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        }


    }


    fun showProgressBar() {
        binding.lottieAnimation.setAnimation(R.raw.progress_bar)
        binding.lottieAnimation.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        binding.lottieAnimation.visibility = View.GONE
    }


}