package com.example.retrofit.adapter

import android.view.ViewGroup
import com.example.retrofit.databinding.FavlayoutBinding
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.model.Favorites
import com.squareup.picasso.Picasso


class FavAdapter(private val favList: ArrayList<Favorites>) : RecyclerView.Adapter<FavAdapter.MyHolder>() {
        private var delBtnListeber: DelButtonClickListener? = null

        interface DelButtonClickListener {
            fun onDelButtonClick(item: Favorites)
        }

        fun setOnDelClickListener(listener: DelButtonClickListener) {
            delBtnListeber = listener
        }

        inner class MyHolder(private val binding: FavlayoutBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: Favorites) {
                Picasso.get().load(item.largeImageURL).into(binding.photo)

                binding.deletebtn.setOnClickListener {
                    delBtnListeber?.onDelButtonClick(item)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = FavlayoutBinding.inflate(inflater, parent, false)
            return MyHolder(binding)
        }

        override fun onBindViewHolder(holder: MyHolder, position: Int) {
            val currentItem = favList[position]
            holder.bind(currentItem)
        }

        override fun getItemCount(): Int {
            return favList.size
        }
    }

