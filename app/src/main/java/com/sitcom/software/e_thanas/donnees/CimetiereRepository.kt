package com.sitcom.software.e_thanas.donnees

import android.content.Context
import com.sitcom.software.e_thanas.classes.Cimetiere
import com.sitcom.software.e_thanas.outils.Serializer
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException

class CimetiereRepository(private val context: Context) {

    fun getCimetieres(): List<Cimetiere> {
        val parser = Serializer.getXmlPullParser("bd_ethana", context)
        val cimetieres = mutableListOf<Cimetiere>()

        try {
            var idCimetiere = 0
            var nomCimetiere = ""
            var rue = ""
            var ville = ""
            var cp = 0

            while (parser?.eventType != XmlPullParser.END_DOCUMENT) {
                when (parser?.eventType) {
                    XmlPullParser.START_TAG -> {
                        val tagName = parser.name
                        if (tagName == "table" && parser.getAttributeValue(null, "name") == "cimetieres") {
                            idCimetiere = 0
                            nomCimetiere = ""
                            rue = ""
                            ville = ""
                            cp = 0
                        } else if (tagName == "column") {
                            val columnName = parser.getAttributeValue(null, "name")
                            val columnValue = parser.nextText()
                            when (columnName) {
                                "idCimetiere" -> idCimetiere = columnValue.toInt()
                                "nomCimetiere" -> nomCimetiere = columnValue
                                "rue" -> rue = columnValue
                                "ville" -> ville = columnValue
                                "cp" -> cp = columnValue.toInt()
                            }
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if (parser.name == "table" && parser.getAttributeValue(null, "name") == "cimetieres") {
                            cimetieres.add(Cimetiere(idCimetiere, nomCimetiere, rue, ville, cp))
                        }
                    }
                }
                parser?.next()
            }
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return cimetieres
    }
}