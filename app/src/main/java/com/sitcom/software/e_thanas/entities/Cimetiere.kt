package com.sitcom.software.e_thanas.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Cimtiere")
data class Cimetiere(
    @PrimaryKey val cimetiere: Int,
    val CP: Int,
    val rue: String,
    val ville: String
)