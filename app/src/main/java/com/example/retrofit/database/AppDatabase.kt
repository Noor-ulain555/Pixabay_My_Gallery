package com.example.retrofit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.retrofit.model.Favorites
import com.example.retrofit.model.Hit

@Database(entities = [Hit::class,Favorites::class], version = 7)
abstract class AppDatabase: RoomDatabase(){

    abstract fun db():Dao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getquoteDatabase(context: Context): AppDatabase {
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