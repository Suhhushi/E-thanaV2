package com.sitcom.software.e_thanas.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sitcom.software.e_thanas.entities.Cimetiere
import java.util.Date

@Dao
interface CimetiereDao {
    @Query("SELECT * FROM Cimtiere")
    fun getAll(): List<Cimetiere>

    @Insert
    fun insert(cimetiere: Cimetiere)
}