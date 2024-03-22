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
            var currentDefunt: Defunt? = null // Nouvelle variable pour stocker le défunt en cours de traitement
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
                                    "defunts" -> { // Ajouter le parsing des défunts
                                        currentDefunt = parseDefunt(parser)
                                        currentDefunt?.let {
                                            // Assurez-vous que le cimetière actuel n'est pas null avant d'ajouter le défunt
                                            currentCimetiere?.let { cimetiere ->
                                                cimetiere.defunts.add(it)
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
                    }
                }
            }
        }
        return Defunt(id, nom, nomJeuneFille, prenom, dateNaissance, dateDeces, idSepulture)
    }



    private fun parseCimetiere(parser: XmlPullParser): Cimetiere {
        var id = 0
        var nom = "Choisir un cimetière ..."
        var rue = ""
        var ville = "Choisir une ville ..."
        var codePostal = 0
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

}