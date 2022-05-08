package com.example.foodapp.presention.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.data.model.MealDB
import com.example.foodapp.data.model.RandomResponse
import com.example.foodapp.databinding.FragmentPopularItemsBinding
import com.example.foodapp.presention.activity.MainActivity
import com.example.foodapp.presention.viewmodel.home.HomeViewModel
import com.example.foodapp.util.Resource
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import java.util.regex.Pattern


class PopularItemsFragment : Fragment() {


    lateinit var binding: FragmentPopularItemsBinding
    lateinit var homeViewModel: HomeViewModel
    val args: PopularItemsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popular_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPopularItemsBinding.bind(view)
        homeViewModel = (activity as MainActivity).homeViewModel
        lifecycle.addObserver(binding.youtubePlayerView)
        showPopularData()
    }

    private fun showPopularData() {
        binding.progressBar.visibility = View.VISIBLE


        Glide.with(binding.imgMealDetail.context)
            .load(args.selectedPopularItem.strMealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolbar.title = args.selectedPopularItem.strMeal
        homeViewModel.getConnect()
        homeViewModel.connectData.observe(viewLifecycleOwner, Observer {
            if (it == "ok") {
                binding.progressBar.visibility = View.GONE
                getPopularData()
            }else{
                binding.progressBar.visibility = View.VISIBLE

            }
        })
    }

    private fun getPopularData() {
        homeViewModel.getMealByID(args.selectedPopularItem.idMeal)
        homeViewModel.mealByID.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    binding.progressBar.visibility=View.GONE
                    response.data?.let {
                        setTextView(it)
                    }
                }
                is Resource.Loading->{

                    binding.progressBar.visibility=View.VISIBLE

                }
                is Resource.Error->{

                    binding.progressBar.visibility=View.GONE
                    response.message?.let {

                        Log.i("IN", "NO IMAGE")
                        Toast.makeText(activity, "An error occurred : $it", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        })
    }

    private fun setTextView(randomResponse: RandomResponse) {

        var meals = randomResponse.meals
        var m=meals[0]
        homeViewModel.getMealSavedByID(m.idMeal).observe(viewLifecycleOwner, Observer {
            try {
                if(it!!.mealId==m.idMeal!!.toInt()&&it!!.mealId!=null){
                    binding.btnSave.setImageResource(R.drawable.love)

                }else{
                    binding.btnSave.setImageResource(R.drawable.heart)
                }
            }catch (e:Exception){
                binding.btnSave.setImageResource(R.drawable.heart)
              //  Toast.makeText(context,"Error ocure ${e.message}",Toast.LENGTH_LONG).show()
            }

        })
        var ytUrl = m.strYoutube
        var id = getVideoId(ytUrl.toString())
        binding.apply {
            tvInstructions.text = "- Instructions : "
            tvContent.text = m.strInstructions
            tvAreaInfo.visibility = View.VISIBLE
            tvCategoryInfo.visibility = View.VISIBLE
            tvAreaInfo.text = tvAreaInfo.text.toString() + m.strArea
            tvCategoryInfo.text = tvCategoryInfo.text.toString() + m.strCategory
            youtubePlayerView.visibility = View.VISIBLE
            var mealDB = MealDB(
                m.idMeal!!.toInt(),
                m.strMeal,
                m.strArea,
                m.strCategory,
                m.strInstructions,
                m.strMealThumb,
                ytUrl
            )
            youtubePlayerView.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                    if (ytUrl != null) {
                        youTubePlayer.loadVideo(id.toString(), 0f)
                        //   youTubePlayer.cueVideo(ytUrl, 0f)
                    }
                }
            })

            btnSave.setOnClickListener {
                homeViewModel.saveFoodInFavorites(mealDB)
                binding.btnSave.setImageResource(R.drawable.love)
                Toast.makeText(context, "Data Saved....", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.youtubePlayerView.release()
    }

    fun getVideoId(videoUrl: String): String? {
        var videoId = ""
        val regex =
            "http(?:s)?:\\/\\/(?:m.)?(?:www\\.)?youtu(?:\\.be\\/|be\\.com\\/(?:watch\\?(?:feature=youtu.be\\&)?v=|v\\/|embed\\/|user\\/(?:[\\w#]+\\/)+))([^&#?\\n]+)"
        val pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(videoUrl)
        if (matcher.find()) {
            videoId = matcher.group(1)
        }
        return videoId
    }
}