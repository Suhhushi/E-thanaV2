package com.sitcom.software.e_thanas.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Defunts")
data class Defunts(
    @PrimaryKey val id_defunts: Int,
    val nom: String,
    val nom_jeune_fille: String,
    val prenom: String,
    val date_naiss: Date,
    val date_deces: Date,
    val id_sep: Int
)