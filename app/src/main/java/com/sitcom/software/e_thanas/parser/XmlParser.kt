package com.sitcom.software.e_thanas.parser

import android.content.Context
import android.util.Xml
import com.sitcom.software.e_thanas.classes.*
import org.xmlpull.v1.XmlPullParser
import java.text.SimpleDateFormat
import java.util.*

object XmlParser {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun parseXml(context: Context): List<Cimetiere> {
        val cimetieres = mutableListOf<Cimetiere>()
        var parser: XmlPullParser? = null
        try {
            val resourceId = context.resources.getIdentifier("bd_ethana", "xml", context.packageName)
            parser = context.resources.getXml(resourceId)
            var eventType = parser.eventType
            var currentCimetiere: Cimetiere? = null
            var currentDefunt: Defunt? = null
            var currentSepulture: Sepulture? = null
            var currentEnregistrement: Enregistrement? = null
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        when (parser.name) {
                            "table" -> {
                                val tableName = parser.getAttributeValue(null, "name")
                                when (tableName) {
                                    "cimetieres" -> {
                                        currentCimetiere = parseCimetiere(parser)
                                        currentCimetiere?.let { cimetieres.add(it) }
                                    }
                                    "defunts" -> {
                                        currentDefunt = parseDefunt(parser)
                                        currentDefunt?.let {
                                            currentCimetiere?.let { cimetiere ->
                                                cimetiere.defunts.add(it)
                                            }
                                        }
                                    }
                                    "sepultures" -> {
                                        currentSepulture = parseSepulture(parser)
                                        currentSepulture?.let {
                                            currentCimetiere?.let { cimetiere ->
                                                cimetiere.sepulture.add(it)
                                            }
                                        }
                                    }
                                    "enregistrer" -> {
                                        currentEnregistrement = parseEnregistrement(parser)
                                        currentEnregistrement?.let {
                                            currentCimetiere?.let { cimetiere ->
                                                cimetiere.enregistrement.add(it)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return cimetieres
    }


    private fun parseDefunt(parser: XmlPullParser): Defunt {
        var id = 0
        var nom = ""
        var nomJeuneFille = ""
        var prenom = ""
        var sexe = ""
        var dateNaissance = "" // Modification pour accepter les valeurs nullables
        var dateDeces = "" // Modification pour accepter les valeurs nullables
        var idSepulture = 0
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "column" -> {
                    val columnName = parser.getAttributeValue(null, "name")
                    val columnValue = parser.nextText()
                    when (columnName) {
                        "idDefunt" -> id = columnValue.toInt()
                        "nom" -> nom = columnValue
                        "nomJeuneFille" -> nomJeuneFille = columnValue
                        "prenom" -> prenom = columnValue
                        "dateNaiss" -> dateNaissance = columnValue
                        "dateDeces" -> dateDeces = columnValue
                        "idSepulture" -> idSepulture = columnValue.toInt()
                        "sexe" -> sexe = columnValue
                    }
                }
            }
        }
        return Defunt(id, nom, nomJeuneFille, prenom, dateNaissance, dateDeces, sexe, idSepulture)
    }

    private fun parseEnregistrement(parser: XmlPullParser): Enregistrement {
        var idEnregistrement = 0
        var idDefunt = 0
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "column" -> {
                    val columnName = parser.getAttributeValue(null, "name")
                    val columnValue = parser.nextText()
                    when (columnName) {
                        "idEnregistrement" -> idEnregistrement = columnValue.toInt()
                        "idDefunt" -> idDefunt = columnValue.toInt()
                    }
                }
            }
        }
        return Enregistrement(idEnregistrement, idDefunt)
    }

    private fun parseCimetiere(parser: XmlPullParser): Cimetiere {
        var id = 0
        var nom = "Choisir un cimetière ..."
        var rue = ""
        var ville = "Choisir une ville ..."
        var codePostal = 0
        val enregistrements = mutableListOf<Int>()
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "column" -> {
                    val columnName = parser.getAttributeValue(null, "name")
                    val columnValue = parser.nextText()
                    when (columnName) {
                        "idCimetiere" -> id = columnValue.toInt()
                        "nomCimetiere" -> nom = columnValue
                        "rue" -> rue = columnValue
                        "ville" -> ville = columnValue
                        "cp" -> codePostal = columnValue.toInt()
                    }
                }
            }
        }
        return Cimetiere(id, nom, rue, ville, codePostal)
    }

    private fun parseSepulture(parser: XmlPullParser): Sepulture {
        var id = 0
        var coordX = 0.0
        var coordY = 0.0
        var idType = 0
        var idCimetiere = 0
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "column" -> {
                    val columnName = parser.getAttributeValue(null, "name")
                    val columnValue = parser.nextText()
                    when (columnName) {
                        "idSepulture" -> id = columnValue.toInt()
                        "coordX" -> coordX = columnValue.toDouble()
                        "coordY" -> coordY = columnValue.toDouble()
                        "idType" -> idType = columnValue.toInt()
                        "idCimetiere" -> idCimetiere = columnValue.toInt()
                    }
                }
            }
        }
        return Sepulture(id, coordX, coordY, idType, idCimetiere)
    }

}
