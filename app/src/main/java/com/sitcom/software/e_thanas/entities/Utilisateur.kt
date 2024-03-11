package com.sitcom.software.e_thanas.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Utilisateur")
data class Utilisateur(
    @PrimaryKey val id_utilisateur: Int,
    val nom: String,
    val prenom: String,
    val mail: String,
    val mdp: String
)