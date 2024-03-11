package com.sitcom.software.e_thanas.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sitcom.software.e_thanas.entities.Utilisateur
import java.util.Date

@Dao
interface UtilisateurDao {
    @Query("SELECT * FROM Utilisateur")
    fun getAll(): List<Utilisateur>

    @Insert
    fun insert(utilisateur: Utilisateur)
}