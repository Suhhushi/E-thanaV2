package com.sitcom.software.e_thanas.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Sepulture")
data class Sepulture(
    @PrimaryKey val id_sep: Int,
    val cimetiere: Int,
    val id_type: Int
)