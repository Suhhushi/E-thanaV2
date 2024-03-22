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

    fun parseDefunts(context: Context): List<Defunt> {
        val defunts = mutableListOf<Defunt>()
        try {
            val resourceId = context.resources.getIdentifier("bd_ethana", "xml", context.packageName)
            val parser = context.resources.getXml(resourceId)
            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && parser.name == "table" && parser.getAttributeValue(null, "name") == "defunts") {
                    while (parser.next() != XmlPullParser.END_TAG) {
                        if (parser.eventType == XmlPullParser.START_TAG && parser.name == "table") {
                            val defunt = parseDefunt(parser)
                            defunts.add(defunt)
                        }
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return defunts
    }

    private fun parseCimetiere(parser: XmlPullParser): Cimetiere {
        var id = 0
        var nom = "Cimetière"
        var rue = ""
        var ville = "Ville"
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

    private fun parseDefunt(parser: XmlPullParser): Defunt {
        var id = 0
        var nom = ""
        var nomJeuneFille: String? = null
        var prenom = ""
        var dateNaissance: Date? = null
        var dateDeces: Date? = null
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
                        "dateNaiss" -> dateNaissance = dateFormat.parse(columnValue)
                        "dateDeces" -> dateDeces = dateFormat.parse(columnValue)
                        "idSepulture" -> idSepulture = columnValue.toInt()
                    }
                }
            }
        }
        return Defunt(id, nom, nomJeuneFille, prenom, dateNaissance!!, dateDeces!!, idSepulture)
    }

    private fun parseSepulture(parser: XmlPullParser): Sepulture {
        var id = 0
        var coordX = ""
        var coordY = ""
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
                        "coordX" -> coordX = columnValue
                        "coordY" -> coordY = columnValue
                        "idType" -> idType = columnValue.toInt()
                        "idCimetiere" -> idCimetiere = columnValue.toInt()
                    }
                }
            }
        }
        return Sepulture(id, coordX, coordY, idType, idCimetiere)
    }

}
