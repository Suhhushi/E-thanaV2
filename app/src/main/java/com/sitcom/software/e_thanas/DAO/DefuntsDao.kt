package com.sitcom.software.e_thanas.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sitcom.software.e_thanas.entities.Defunts
import java.util.Date

@Dao
interface DefuntsDao {
    @Query("SELECT * FROM Defunts")
    fun getAll(): List<Defunts>

    @Insert
    fun insert(defunts: Defunts)
}