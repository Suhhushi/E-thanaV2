package com.sitcom.software.e_thanas.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sitcom.software.e_thanas.DAO.*
import com.sitcom.software.e_thanas.entities.*

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
}
