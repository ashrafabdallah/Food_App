package com.example.foodapp.presention.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentSplashBinding
import com.example.foodapp.presention.activity.MainActivity
import com.example.foodapp.presention.viewmodel.splash.SplashViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception


class SplashFragment : Fragment() {

 val RequestCode=0
    lateinit var binding: FragmentSplashBinding
    lateinit var splashviewModel: SplashViewModel
lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

       val  view=inflater.inflate(R.layout.fragment_splash, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSplashBinding.bind(view)
        splashviewModel = (activity as MainActivity).splashViweModel
       navController=(activity as MainActivity).navController

        splashviewModel.connectOrDisconnect()
        splashviewModel.liveData.observe(viewLifecycleOwner, Observer {
            CoroutineScope(Dispatchers.Main).launch {
               try {
                   if(it=="Ok"){
                       delay(2000)
                       findNavController().navigate(R.id.action_splashFragment_to_homefragment)
                   }else{
                       binding.animationView.setAnimation(R.raw.connection_error)
                       Snackbar.make(view,"Please Open Internet>>>",Snackbar.LENGTH_LONG).apply {
                           setAction("Open Internet"){
                               startActivityForResult(Intent(Settings.ACTION_WIRELESS_SETTINGS),RequestCode)

                           }
                           show()
                       }
                   }
               }catch (E:Exception){

               }

            }
        })



    }


}