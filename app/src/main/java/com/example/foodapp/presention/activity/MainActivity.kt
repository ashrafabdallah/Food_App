package com.example.foodapp.presention.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMainBinding
import com.example.foodapp.presention.adapters.*
import com.example.foodapp.presention.viewmodel.home.HomeViewModel
import com.example.foodapp.presention.viewmodel.home.HomeViewModelFactory
import com.example.foodapp.presention.viewmodel.splash.SplashViewModel
import com.example.foodapp.presention.viewmodel.splash.SplashViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener
    {

    @Inject
    lateinit var splashViweModelFactory: SplashViewModelFactory
    lateinit var splashViweModel: SplashViewModel

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory
    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var popularAdapter: PopularAdapter

    @Inject
    lateinit var homeCategoryAdapter: HomeCategoryAdapter

    @Inject
    lateinit var CategoryItemAdapter: CategoryItemAdapter

    @Inject
    lateinit var searchAdapter: SearchAdapter

    @Inject
    lateinit var favoritAdapter: FavoritAdapter
    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashViweModel =
            ViewModelProvider(this, splashViweModelFactory).get(SplashViewModel::class.java)
        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)


        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController

        binding.BottomNavigationView.setupWithNavController(navController)


    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {

            R.id.splashFragment -> {
                Log.i("TAG", "Splash")
                binding.BottomNavigationView.visibility = View.GONE
            }
            R.id.homefragment -> {
                Log.i("TAG", "Home")
                binding.BottomNavigationView.visibility = View.VISIBLE

            }
            R.id.categoryFragment -> {
                Log.i("TAG", "Category")

                binding.BottomNavigationView.visibility = View.VISIBLE
            }
            R.id.favoritsFragment -> {
                Log.i("TAG", "favoritsFragment")

                binding.BottomNavigationView.visibility = View.VISIBLE
            }
        }
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(this)

    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(this)

    }

    //  Use This Function To  remove fragment from backstack in navigation component
    override fun onBackPressed() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navigationController = navHost.navController
        //  val navigationController = binding.navigation.findNavController()
        if (navigationController.currentDestination?.id == R.id.homefragment) {
            finish()
        } else if (navigationController.currentDestination?.id == R.id.favoritsFragment) {
            navigationController.navigate(R.id.homefragment)

        } else if (navigationController.currentDestination?.id == R.id.categoryFragment) {
            navigationController.navigate(R.id.homefragment)
        } else {
            super.onBackPressed()
        }
    }








}