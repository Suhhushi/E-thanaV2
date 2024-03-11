package com.sitcom.software.e_thanas.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Favoris", primaryKeys = ["id_defunts", "id_utilisateur"])
data class Favoris(
    val id_defunts: Int,
    val id_utilisateur: Int
)