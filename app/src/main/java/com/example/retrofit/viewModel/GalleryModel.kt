package com.example.retrofit.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofit.model.Favorites
import com.example.retrofit.model.Hit
import com.example.retrofit.model.GalleryImg
import com.example.retrofit.repository.GalleryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GalleryModel(private val Repository: GalleryRepository):ViewModel() {

    val images: LiveData<GalleryImg> = Repository.images

    fun getfav():LiveData<List<Favorites>>{
        return Repository.getfavorites()
    }

    fun delfav(item: Favorites){
        viewModelScope.launch(Dispatchers.IO) {
            Repository.delfavorites(item)
        }

    }
    fun insert(hit: Hit){
        val fav = Favorites(
            0,
            hit.favId,
            hit.largeImageURL,
            hit.id,
            hit.likes,
            hit.views,
            hit.tags,
            hit.type,
            hit.downloads,
            hit.comments,
            hit.user

        )
        viewModelScope.launch(Dispatchers.IO) {
            Repository.favorites(fav)

        }
    }
    val picRetro:LiveData<GalleryImg>
        get() = Repository.picRetro



    fun loadImagesByCategory(category: String, function: () -> Unit) {
        viewModelScope.launch {
            Repository.getImages(1, category)
        }
    }

    // Suspend function to check if an image is a favorite
    suspend fun isFavorite(id: Int): Boolean {
        return Repository.isFavorite(id)
    }
    fun removeFavorite(favorite: Favorites) {
        viewModelScope.launch {
            Repository.removeFavorite(favorite)
        }
    }

    fun fetchFavoriteImageIds() {
        viewModelScope.launch {
            Repository.fetchFavoriteImageIds()
        }
    }
    fun toggleFavorite(image: Hit) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository.toggleFavorite(image)
        }
    }

    fun getFavorites() = Repository.getFavorites()
    suspend fun getGallery():List<Hit>{
        return Repository.getGallery()
    }

}