package com.example.retrofit.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.retrofit.api.GalleryAPIs
import com.example.retrofit.database.AppDatabase
import com.example.retrofit.model.Favorites
import com.example.retrofit.model.Hit
import com.example.retrofit.model.GalleryImg
import com.example.retrofit.utils.networkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GalleryRepository(
    private val galleryAPIs: GalleryAPIs,
    private val AppDatabase: AppDatabase,
    private val applicationContext: Context
) {
    private val picLiveData = MutableLiveData<GalleryImg>()

    val favoriteImageIdsLiveData: MutableLiveData<List<Int>> = MutableLiveData()
    val images: LiveData<GalleryImg>
        get() = picLiveData


    suspend fun favorites(fa: Favorites) {
        AppDatabase.db().insertfav(fa)
    }

    fun getfavorites(): LiveData<List<Favorites>> {
        return AppDatabase.db().getfav()
    }

    suspend fun delfavorites(item: Favorites) {
        AppDatabase.db().delfav(item.favoriteId)
    }

    suspend fun getImages(page: Int, category: String) {
        if (networkUtils.isInternetAvailable(applicationContext)) {

            val result = galleryAPIs.getAPIs(category, page)

            if (result.body() != null) {

                AppDatabase.db().addImages(result.body()!!.hits)
                picLiveData.postValue(result.body())
            }
        } else {

            val images = AppDatabase.db().getImages()
            val imageList = GalleryImg(images, 1, 1)
            picLiveData.postValue(imageList)
        }
    }

    suspend fun removeFavorite(favorite: Favorites) {
        withContext(Dispatchers.IO) {
            AppDatabase.db().removeFavorite(favorite)
        }
    }

    suspend fun toggleFavorite(image: Hit) {
        withContext(Dispatchers.IO) {
            val isFavorite = AppDatabase.db().isFavorite(image.id)

            if (isFavorite) {
                AppDatabase.db().removeFavoriteByImageId(image.id)
            } else {
                val favoriteEntity = Favorites(
                0,
                    imageId = image.imageId,
                    largeImageURL = image.largeImageURL,
                    id = image.id,
                    likes = image.likes,
                    views = image.views,
                    tags = image.tags,
                    type = image.type,
                    downloads = image.downloads,
                    comments = image.comments,
                    user = image.user,
                )
                AppDatabase.db().addFavorite(favoriteEntity)
            }
        }
    }

    fun getFavorites(): LiveData<List<Favorites>> {
        return AppDatabase.db().getFavoritesLiveData()
    }

    suspend fun isFavorite(imageId: Int): Boolean {
        return AppDatabase.db().isFavorite(imageId)
    }

    suspend fun fetchFavoriteImageIds() {
        val favoriteImageIds = AppDatabase.db().getFavoriteImageIds()
        favoriteImageIdsLiveData.value = favoriteImageIds
    }

    suspend fun getGallery(): List<Hit> {
        return AppDatabase.db().getGallery()
    }
}