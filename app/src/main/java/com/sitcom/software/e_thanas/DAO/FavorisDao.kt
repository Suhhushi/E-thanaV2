package com.sitcom.software.e_thanas.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sitcom.software.e_thanas.entities.Favoris
import java.util.Date

@Dao
interface FavorisDao {
    @Query("SELECT * FROM Favoris")
    fun getAll(): List<Favoris>

    @Insert
    fun insert(favoris: Favoris)
}