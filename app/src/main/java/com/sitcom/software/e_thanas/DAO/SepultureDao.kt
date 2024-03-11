package com.sitcom.software.e_thanas.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sitcom.software.e_thanas.entities.Sepulture
import java.util.Date
@Dao
interface SepultureDao {
    @Query("SELECT * FROM Sepulture")
    fun getAll(): List<Sepulture>

    @Insert
    fun insert(sepulture: Sepulture)
}