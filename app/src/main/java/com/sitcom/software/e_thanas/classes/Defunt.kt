package com.sitcom.software.e_thanas.classes

import java.util.*

data class Defunt(
    val id: Int,
    val nom: String,
    val nomJeuneFille: String?,
    val prenom: String,
    val dateNaissance: String,
    val dateDeces: String,
    val idSepulture: Int
)