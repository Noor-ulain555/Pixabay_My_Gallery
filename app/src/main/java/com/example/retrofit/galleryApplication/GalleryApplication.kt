package com.example.retrofit.galleryApplication

import android.app.Application
import com.example.retrofit.api.GalleryAPIs
import com.example.retrofit.api.RetrofitHelper
import com.example.retrofit.database.AppDatabase
import com.example.retrofit.repository.GalleryRepository

class GalleryApplication:Application() {

    lateinit var GalleryRepository: GalleryRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }
    private fun initialize(){
        val galleryAPIs =RetrofitHelper.getInstance().create(GalleryAPIs::class.java)
        val database= AppDatabase.getDatabase(applicationContext)
        GalleryRepository =GalleryRepository(galleryAPIs,database,applicationContext)

    }
}