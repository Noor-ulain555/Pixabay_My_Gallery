package com.example.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.adapter.FavAdapter
import com.example.retrofit.galleryApplication.GalleryApplication
import com.example.retrofit.databinding.ActivityFavoritesBinding
import com.example.retrofit.model.Favorites
import com.example.retrofit.viewModel.Viewmodelfactory
import com.example.retrofit.viewModel.GalleryModel

class favoritesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityFavoritesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorites)

        val repository = (application as GalleryApplication).GalleryRepository
        val mainviewModel: GalleryModel = ViewModelProvider(
            this,
            Viewmodelfactory(repository)
        ).get(GalleryModel::class.java)


        recyclerView = binding.FavoritesRecycler
        recyclerView.layoutManager = LinearLayoutManager(this)

        mainviewModel.getfav().observe(this, Observer { it ->
            val adaptor = FavAdapter(it as ArrayList<Favorites>)
            recyclerView.adapter = adaptor


            adaptor.setOnDelClickListener(object : FavAdapter.DelButtonClickListener {

                override fun onDelButtonClick(item: Favorites) {
                    mainviewModel.delfav(item)

                }

            })
        })


    }

}