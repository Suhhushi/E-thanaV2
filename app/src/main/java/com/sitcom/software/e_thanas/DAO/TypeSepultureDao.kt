package com.sitcom.software.e_thanas.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sitcom.software.e_thanas.entities.TypeSepulture
import java.util.Date

@Dao
interface TypeSepultureDao {
    @Query("SELECT * FROM type_sepulture")
    fun getAll(): List<TypeSepulture>

    @Insert
    fun insert(typeSepulture: TypeSepulture)
}