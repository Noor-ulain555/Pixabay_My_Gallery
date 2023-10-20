package com.example.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.retrofit.databinding.ActivityDetailScreenBinding
import com.example.retrofit.model.HitFav
import com.squareup.picasso.Picasso

class Detail_Screen : AppCompatActivity() {
    private lateinit var binding: ActivityDetailScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_detail_screen)

        val hit=intent.getSerializableExtra("Hit") as HitFav
        Picasso.get().load(hit.hit.largeImageURL).into(binding.detailsImageView)
        binding.downloadsTextView.text="Downloads ${hit.hit.downloads}"
        binding.tagsTextView.text="${hit.hit.tags}"
        binding.typesTextView.text="${hit.hit.type}"
        binding.userTextView.text="${hit.hit.user}"

    }
}