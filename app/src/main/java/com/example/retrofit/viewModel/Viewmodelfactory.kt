package com.example.retrofit.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.retrofit.repository.GalleryRepository

class Viewmodelfactory(private val GalleryRepository: GalleryRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GalleryModel(GalleryRepository) as T
    }
}