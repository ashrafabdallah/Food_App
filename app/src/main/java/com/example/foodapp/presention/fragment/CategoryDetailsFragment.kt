package com.example.foodapp.presention.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentCategoryDetailsBinding
import com.example.foodapp.presention.activity.MainActivity
import com.example.foodapp.presention.adapters.SearchAdapter
import com.example.foodapp.presention.viewmodel.home.HomeViewModel
import com.example.foodapp.util.Resource


class CategoryDetailsFragment : Fragment() {

    lateinit var binding: FragmentCategoryDetailsBinding
    lateinit var homeViewModel: HomeViewModel
    lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoryDetailsBinding.bind(view)
        homeViewModel = (activity as MainActivity).homeViewModel
        searchAdapter=(activity as MainActivity).searchAdapter
        val args: CategoryDetailsFragmentArgs by navArgs()
        homeViewModel.getConnect()
        homeViewModel.connectData.observe(viewLifecycleOwner, Observer {
            if (it == "ok") {
                getCategoryDetails(args.selectedItemCategory)
            } else {
                Toast.makeText(context, "No Internet...", Toast.LENGTH_LONG).show()
            }

        })
        intRecyclerView()
        onClickRecyclerViewItem()
    }

    private fun onClickRecyclerViewItem() {
        searchAdapter.setItemClickListener {
            var bundle = Bundle().apply {
                putSerializable("Category_item_Selected", it)
            }
            findNavController().navigate(R.id.mealDetailsFragment, bundle)
        }
    }

    private fun intRecyclerView() {
        binding.recyclerView.apply {
            adapter=searchAdapter
            layoutManager=LinearLayoutManager(context)
        }
    }

    private fun getCategoryDetails(s: String) {
        homeViewModel.getMealByName(s)
        homeViewModel.mealByName.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let {
                        if(it.meals!=null){
                            searchAdapter.differ.submitList(it.meals)
                        }else{
                            Toast.makeText(context,"No Data.....",Toast.LENGTH_LONG).show()
                            findNavController().navigate(R.id.categoryFragment)
                        }

                    }
                }
                is Resource.Error->{
                    hideProgressBar()
                }
                is Resource.Loading->{
                    showProgressBar()
                }
            }

        })
    }


    fun showProgressBar() {
        binding.progressBar2.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        binding.progressBar2.visibility = View.GONE
    }
}