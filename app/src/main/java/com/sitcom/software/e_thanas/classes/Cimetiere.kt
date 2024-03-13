package com.sitcom.software.e_thanas.classes

open class Cimetiere {
    var id: Int = 0
    var nom: String = ""
    var rue: String = ""
    var ville: String = ""
    var cp : String = ""


    constructor(
        id: Int,
        nom: String,
        rue: String,
        ville: String,
        cp: String
    ) {
        this.id = id
        this.nom = nom
        this.rue = rue
        this.ville = ville
        this.cp = cp
    }
}