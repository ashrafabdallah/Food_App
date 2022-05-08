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
import com.example.foodapp.databinding.FragmentSearchragmentBinding
import com.example.foodapp.presention.activity.MainActivity
import com.example.foodapp.presention.adapters.SearchAdapter
import com.example.foodapp.presention.viewmodel.home.HomeViewModel
import com.example.foodapp.util.Resource


class Searchragment : Fragment() {
    lateinit var binding: FragmentSearchragmentBinding
    lateinit var homeViewModel: HomeViewModel
    lateinit var searchAdapter: SearchAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_searchragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchragmentBinding.bind(view)
        homeViewModel = (activity as MainActivity).homeViewModel
        searchAdapter = (activity as MainActivity).searchAdapter

        val args: SearchragmentArgs by navArgs()
        var query = args.querySearch
        homeViewModel.getConnect()
        homeViewModel.connectData.observe(viewLifecycleOwner, Observer {
            if (it == "ok") {
                getDataFromSearch(query)
            } else {
                Toast.makeText(context, "No Internet....", Toast.LENGTH_LONG).show()
            }
        })
        intRecyclerView()
        onClickRecyclerViewItem()
    }

    private fun intRecyclerView() {

        binding.recyclerViewSearch.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    fun getDataFromSearch(query: String?) {
        if (query != null) {
            homeViewModel.getMealByName(query)
            homeViewModel.mealByName.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgress()
                        response.data?.let {


                            if (it.meals != null) {
                                searchAdapter.differ.submitList(it.meals)

                            } else {
                                it.meals = emptyList()
                                Toast.makeText(context, "No Data.....", Toast.LENGTH_LONG).show()
                                searchAdapter.notifyDataSetChanged()
                                findNavController().navigate(R.id.homefragment)
                            }
                        }
                    }
                    is Resource.Error -> {
                        hideProgress()
                        response.message?.let {
                            Toast.makeText(context, "${it}", Toast.LENGTH_LONG).show()
                        }

                    }
                    is Resource.Loading -> {
                        showProgress()


                    }

                }

            })
        }

    }

    private fun hideProgress() {
        binding.progressBarSearch.visibility = View.GONE
    }

    private fun showProgress() {
        binding.progressBarSearch.visibility = View.VISIBLE
    }

    private fun onClickRecyclerViewItem() {

        searchAdapter.setItemClickListener {

            var bundle = Bundle().apply {
                putSerializable("search_selected_item", it)

            }
            findNavController().navigate(R.id.mealDetailsFragment, bundle)
        }
    }
}