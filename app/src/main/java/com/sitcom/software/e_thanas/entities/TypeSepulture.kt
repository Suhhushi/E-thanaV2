package com.sitcom.software.e_thanas.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "type_sepulture")
data class TypeSepulture(
    @PrimaryKey val id_type: Int,
    val nom: String
)