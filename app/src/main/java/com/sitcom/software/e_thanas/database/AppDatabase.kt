package com.sitcom.software.e_thanas.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sitcom.software.e_thanas.DAO.*
import com.sitcom.software.e_thanas.entities.*
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [Cimetiere::class, TypeSepulture::class, Utilisateur::class, Sepulture::class, Defunts::class, Favoris::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cimetiereDao(): CimetiereDao
    abstract fun typeSepultureDao(): TypeSepultureDao
    abstract fun utilisateurDao(): UtilisateurDao
    abstract fun sepultureDao(): SepultureDao
    abstract fun defuntsDao(): DefuntsDao
    abstract fun favorisDao(): FavorisDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "my-database"
                )
                    .addCallback(AppDatabaseCallback(scope, INSTANCE!!))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
