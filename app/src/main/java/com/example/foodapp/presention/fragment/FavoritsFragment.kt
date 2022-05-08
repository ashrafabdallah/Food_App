package com.example.foodapp.presention.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentFavoritsBinding
import com.example.foodapp.presention.activity.MainActivity
import com.example.foodapp.presention.adapters.FavoritAdapter
import com.example.foodapp.presention.viewmodel.home.HomeViewModel
import com.google.android.material.snackbar.Snackbar


class FavoritsFragment : Fragment() {


    lateinit var binding: FragmentFavoritsBinding
    lateinit var favoritAdapter: FavoritAdapter
    lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.fragment_favorits, container, false)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoritsBinding.bind(view)
        favoritAdapter = (activity as MainActivity).favoritAdapter
        homeViewModel = (activity as MainActivity).homeViewModel
        showTextEmpty()
        showDataInRecycler()
        intResyclerView()
        daleteMeal()
        onClickRecyclerViewItem()
    }

    private fun showTextEmpty() {
        if (favoritAdapter.differ.currentList.size == 0) {
            binding.textEmpty.visibility = View.VISIBLE

        } else {
            binding.textEmpty.visibility = View.GONE
        }


    }

    private fun daleteMeal() {

        val itemCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val postion = viewHolder.bindingAdapterPosition
                var foods = favoritAdapter.differ.currentList[postion]
                homeViewModel.deleteMealFromDataBase(foods)

                view?.let {
                    Snackbar.make(it, "Delete Success ....", Snackbar.LENGTH_LONG)
                        .apply {
                            setAction("UnDo") {
                                homeViewModel.saveFoodInFavorites(foods)
                                binding.textEmpty.visibility = View.GONE
                            }
                            show()

                        }
                    if (postion == 0) {
                        binding.textEmpty.visibility = View.VISIBLE
                    }
                }

            }
        }
        ItemTouchHelper(itemCallBack).apply {
            attachToRecyclerView(binding.recyclerViewFavorits)

        }


    }

    private fun showDataInRecycler() {

        homeViewModel.getFavoritsFromDataBase().observe(viewLifecycleOwner, Observer {
            favoritAdapter.differ.submitList(it)
            if (it.isNotEmpty()) {
                binding.textEmpty.visibility = View.GONE
            }


        })
    }

    private fun intResyclerView() {
        binding.recyclerViewFavorits.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoritAdapter

        }
    }

    fun onClickRecyclerViewItem() {
    favoritAdapter.setItemClickListner {
        var bundle = Bundle().apply {
            putSerializable("favorit_item", it)
        }
        findNavController().navigate(R.id.mealDetailsFragment, bundle)
    }
    }

}