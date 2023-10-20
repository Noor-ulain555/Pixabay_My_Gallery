package com.example.retrofit.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.retrofit.model.Favorites
import com.example.retrofit.model.Hit


@Dao
interface Dao {

    @Query("SELECT * FROM gallery")
    suspend fun getGallery(): List<Hit>

    @Insert
    suspend fun insertfav(favorites: Favorites)

    @Query("SELECT * FROM favorites")
    fun getfav(): LiveData<List<Favorites>>

    @Query("DELETE FROM favorites WHERE favoriteId=:favId")
    suspend fun delfav(favId:Int)

    @Insert
    suspend fun addImages(images: List<Hit>)

    @Query("SELECT * FROM gallery")
    suspend fun getImages() : List<Hit>

    @Delete
    suspend fun removeFavorite(favorite: Favorites)

    @Query("SELECT EXISTS (SELECT 1 FROM favorites WHERE id = :id LIMIT 1)")
    suspend fun isFavorite(id: Int): Boolean

    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun removeFavoriteByImageId(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: Favorites)

    @Query("SELECT * FROM favorites")
    fun getFavoritesLiveData(): LiveData<List<Favorites>>

    @Query("SELECT imageId FROM favorites")
    suspend fun getFavoriteImageIds(): List<Int>

    @Query("SELECT imageId FROM favorites")
    fun getFavoriteImageIdsLiveData(): LiveData<List<Int>>
}