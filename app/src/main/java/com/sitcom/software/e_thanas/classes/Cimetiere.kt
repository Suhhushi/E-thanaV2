package com.sitcom.software.e_thanas.classes

data class Cimetiere(
    val id: Int,
    val nom: String,
    val rue: String,
    val ville: String,
    val codePostal: Int,
    val defunts: MutableList<Defunt> = mutableListOf()
)