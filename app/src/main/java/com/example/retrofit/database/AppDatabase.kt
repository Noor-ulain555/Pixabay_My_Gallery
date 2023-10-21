package com.example.retrofit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.retrofit.model.Favorites
import com.example.retrofit.model.Hit

@Database(entities = [Hit::class,Favorites::class], version = 8)
abstract class AppDatabase: RoomDatabase(){

    abstract fun db():Dao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "DB"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}