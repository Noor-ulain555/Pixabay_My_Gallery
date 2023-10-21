package com.example.retrofit

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.adapter.GalleryAdapter
import com.example.retrofit.galleryApplication.GalleryApplication
import com.example.retrofit.databinding.ActivityMainBinding
import com.example.retrofit.model.Favorites
import com.example.retrofit.model.Hit
import com.example.retrofit.model.HitFav
import com.example.retrofit.viewModel.Viewmodelfactory
import com.example.retrofit.viewModel.GalleryModel

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private lateinit var loader: ProgressBar

    lateinit var mainViewModel: GalleryModel

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        loader = binding.loader

        val repository = (application as GalleryApplication).GalleryRepository

        mainViewModel = ViewModelProvider(this, Viewmodelfactory(repository))
            .get(GalleryModel::class.java)


        recyclerView = binding.ImageRecycler
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = GalleryAdapter(this@MainActivity, emptyList(), { clickedImage ->

            toggleFavorite(clickedImage)
            showLoader()

        }, mainViewModel)

        binding.ImageRecycler.adapter = adapter

        val categoryViews = arrayOf(
            binding.background,
            binding.fashion,
            binding.nature,
            binding.science,
            binding.computer,
            binding.food
        )

        categoryViews.forEach { categoryView ->
            categoryView.setOnClickListener {
                showLoader()
                categoryViews.forEach {
                    it.setTextColor(resources.getColor(R.color.light_grey))
                }
                categoryView.setTextColor(resources.getColor(R.color.black))

                val selectedCategory = categoryView.text.toString()
                mainViewModel.loadImagesByCategory(selectedCategory) {
                    hideLoader()
                }
            }
        }

        mainViewModel.images.observe(this) { imageList ->
            Log.d("mycode", imageList.toString())
            val hitsWithFavorites = imageList.hits.map { hit: Hit ->
                val isFavorite = mainViewModel.getFavorites().value?.any { favorite: Favorites ->
                    favorite.id == hit.id
                } ?: false
                HitFav(hit, isFavorite)
            }
            Log.d("mycode", "favs:$hitsWithFavorites")

            adapter.imageList = hitsWithFavorites

            adapter.notifyDataSetChanged()
            hideLoader()
        }

        binding.btnFav.setOnClickListener {
            startActivity(Intent(this@MainActivity, favoritesActivity::class.java))
        }

        hideLoader()
    }

    private fun toggleFavorite(image: Hit) {
        mainViewModel.toggleFavorite(image)
    }

    private fun showLoader() {
        loader.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        loader.visibility = View.GONE
    }
}
