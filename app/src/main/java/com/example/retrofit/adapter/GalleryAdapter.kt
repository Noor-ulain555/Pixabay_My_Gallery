package com.example.retrofit.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.Detail_Screen
import com.example.retrofit.R
import com.example.retrofit.databinding.ImagelayoutBinding
import com.example.retrofit.model.Hit
import com.example.retrofit.model.HitFav
import com.example.retrofit.viewModel.GalleryModel
import com.squareup.picasso.Picasso

class GalleryAdapter(val context: Context, var imageList: List<HitFav>, private val onFavoriteClick:
    (Hit) -> Unit, private val viewModel: GalleryModel,) :
    RecyclerView.Adapter<GalleryAdapter.MyHolder>() {

    inner class MyHolder(private val binding: ImagelayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HitFav) {
            Picasso.get().load(item.hit.largeImageURL).into(binding.photo)


            binding.favouriteBtn.setOnClickListener {
                if (binding.favouriteBtn.drawable.constantState ==
                    ContextCompat.getDrawable(
                        binding.favouriteBtn.context,
                        R.drawable.favorite_border_icon
                    )?.constantState
                ) {
                    binding.favouriteBtn.setImageResource(R.drawable.favorite)
                } else {
                    binding.favouriteBtn.setImageResource(R.drawable.favorite_border_icon)
                }

                toggleFavorite(item.hit)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ImagelayoutBinding.inflate(inflater, parent, false)
        return MyHolder(binding)

    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val currentItem = imageList[position]
        holder.bind(currentItem)

        holder.itemView.setOnClickListener {
            val intent= Intent(context, Detail_Screen::class.java)
            intent.putExtra("Hit",currentItem)
            context.startActivity(intent)
        }

    }

    private fun toggleFavorite(image: Hit) {
        onFavoriteClick(image)
    }
}
