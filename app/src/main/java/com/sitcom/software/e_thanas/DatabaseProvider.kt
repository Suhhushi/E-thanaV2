package com.sitcom.software.e_thanas

import android.content.Context
import androidx.room.Room
import com.sitcom.software.e_thanas.database.AppDatabase

object DatabaseProvider {
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        if (INSTANCE == null) {
            synchronized(AppDatabase::class.java) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "my-database"
                ).build()
            }
        }
        return INSTANCE!!
    }
}