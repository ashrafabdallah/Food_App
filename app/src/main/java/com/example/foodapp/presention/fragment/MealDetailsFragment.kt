package com.example.foodapp.presention.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.data.model.MealDB
import com.example.foodapp.data.model.MealDetail
import com.example.foodapp.databinding.FragmentMealDetailsBinding
import com.example.foodapp.presention.activity.MainActivity
import com.example.foodapp.presention.viewmodel.home.HomeViewModel
import com.example.foodapp.util.Resource
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import java.util.regex.Pattern


class MealDetailsFragment : Fragment() {
    lateinit var binding: FragmentMealDetailsBinding
    lateinit var homeViewModel: HomeViewModel
    var isFavorit: Boolean = false
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMealDetailsBinding.bind(view)
        homeViewModel = (activity as MainActivity).homeViewModel
//        sharedPreferences =
//            requireActivity().getSharedPreferences("sharedPrefFile", Context.MODE_PRIVATE)
//        editor = sharedPreferences.edit()

        lifecycle.addObserver(binding.youtubePlayerView)
        showDataFromNavArgs()

    }

    fun showDataFromNavArgs() {


        val args: MealDetailsFragmentArgs by navArgs()

        val meals = args.selectedItem

        if (meals != null) {
            var mealDB = MealDB(
                meals.idMeal!!.toInt(),
                meals.strMeal,
                meals.strArea,
                meals.strCategory,
                meals.strInstructions,
                meals.strMealThumb,
                meals.strYoutube
            )
            setDataIntoViews(mealDB)

        } else if (args.mealName != null) {

            // get meal Details from api by args.meal_name
            args.mealName?.let {
                getMealDetails(it)

            }
        }
        // get Search item Details
        else if (args.searchSelectedItem != null) {
            showSearchItemDetails(args.searchSelectedItem!!)
        } else if (args.favoritItem != null) {
            setDataIntoViews(args.favoritItem!!)
        }else if(args.categoryItemSelected!=null){
            var mealDB = MealDB(
                args.categoryItemSelected!!.idMeal!!.toInt(),
                args.categoryItemSelected!!.strMeal,
                args.categoryItemSelected!!.strArea,
                args.categoryItemSelected!!.strCategory,
                args.categoryItemSelected!!.strInstructions,
                args.categoryItemSelected!!.strMealThumb,
                args.categoryItemSelected!!.strYoutube
            )
            setDataIntoViews(mealDB)
        }


    }

    private fun showSearchItemDetails(searchSelectedItem: MealDetail) {
        var mealDB = MealDB(
            searchSelectedItem.idMeal!!.toInt(),
            searchSelectedItem.strMeal,
            searchSelectedItem.strArea,
            searchSelectedItem.strCategory,
            searchSelectedItem.strInstructions,
            searchSelectedItem.strMealThumb,
            searchSelectedItem.strYoutube
        )
        setDataIntoViews(mealDB)
    }

    private fun getMealDetails(mealName: String) {
        homeViewModel.getConnect()
        homeViewModel.connectData.observe(viewLifecycleOwner, Observer {
            if (it == "ok") {
                getData(mealName)
            } else {
                Toast.makeText(context, "No Internet Please Open Internet...", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }
    private fun getData(mealName: String) {
        homeViewModel.getMealByID(mealName)
        homeViewModel.mealByID.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        var meals = it.meals[0]
//                        setDataIntoTextView(meal)
                        var mealDB = MealDB(
                            meals.idMeal!!.toInt(),
                            meals.strMeal,
                            meals.strArea,
                            meals.strCategory,
                            meals.strInstructions,
                            meals.strMealThumb,
                            meals.strYoutube
                        )
                        setDataIntoViews(mealDB)

                    }
                }
                is Resource.Error -> {
                    response.message?.let {
                        Toast.makeText(context, "error ${it}", Toast.LENGTH_LONG).show()
                    }
                }

            }

        })

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
    fun setDataIntoViews(meal: MealDB) {
        homeViewModel.getMealSavedByID(meal.mealId.toString())
            .observe(viewLifecycleOwner, Observer {
                try {
                    if (it!!.mealId != null && it!!.mealId == meal.mealId!!.toInt()) {
                        binding.btnSave.setImageResource(R.drawable.love)
                        isFavorit = true
                    } else {
                        isFavorit = false
                        binding.btnSave.setImageResource(R.drawable.heart)
                    }
                } catch (e: Exception) {
                    isFavorit = false
                    binding.btnSave.setImageResource(R.drawable.heart)
                    //Toast.makeText(context,"Error ocure ${e.message}",Toast.LENGTH_LONG).show()
                }

            })

        Glide.with(binding.imgMealDetail.context)
            .load(meal.mealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolbar.title = meal.mealName

        var ytUrl = meal.mealYoutubeLink

        Log.i("YUT", ytUrl.toString())
        Log.i("YUT", getVideoId(ytUrl.toString()).toString())
        var id = getVideoId(ytUrl.toString())
        binding.apply {
            tvInstructions.text = "- Instructions : "
            tvContent.text = meal.mealInstruction
            tvAreaInfo.visibility = View.VISIBLE
            tvCategoryInfo.visibility = View.VISIBLE
            tvAreaInfo.text = tvAreaInfo.text.toString() + meal.mealCountry
            tvCategoryInfo.text = tvCategoryInfo.text.toString() + meal.mealCategory
            youtubePlayerView.visibility = View.VISIBLE



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

                if (isFavorit) {
                    isFavorit = false
                    homeViewModel.deleteMealById(meal.mealId.toString())
                    binding.btnSave.setImageResource(R.drawable.heart)

                } else {
                    isFavorit = true
                    homeViewModel.saveFoodInFavorites(meal)
                    binding.btnSave.setImageResource(R.drawable.love)

                }
            }

        }

    }

}