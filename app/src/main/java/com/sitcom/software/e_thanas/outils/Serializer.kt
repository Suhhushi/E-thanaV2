package com.sitcom.software.e_thanas.outils

import android.content.Context
import android.content.res.XmlResourceParser
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException

abstract class Serializer {

    companion object leSerialiser {

        /**
         * @param filename : nom du fichier XML à lire
         * @param context : contexte de l'application
         * @return : XmlPullParser pour le fichier XML donné
         */
        fun getXmlPullParser(filename: String, context: Context): XmlPullParser? {
            try {
                // Récupère l'identifiant du fichier XML à partir de son nom
                val resourceId = context.resources.getIdentifier(filename, "xml", context.packageName)
                if (resourceId == 0) {
                    // Si le fichier n'existe pas
                    return null
                }
                // Obtient le parser XML à partir du fichier XML
                return context.resources.getXml(resourceId)
            } catch (e: XmlPullParserException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }

        /**
         * @param filename : nom du fichier XML à lire
         * @param context : contexte de l'application
         * @return : données lues à partir du fichier XML
         */
        fun readXml(filename: String, context: Context) {
            val parser = getXmlPullParser(filename, context)
            try {
                while (parser?.eventType != XmlPullParser.END_DOCUMENT) {
                    when (parser?.eventType) {
                        XmlPullParser.START_TAG -> {
                            val tagName = parser.name
                            // Ici, vous pouvez traiter les balises selon vos besoins
                            // Par exemple :
                            // if (tagName == "item") {
                            //     val value = parser.getAttributeValue(null, "attribut_name")
                            //     // Faites quelque chose avec la valeur
                            // }
                        }
                    }
                    parser?.next()
                }
            } catch (e: XmlPullParserException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
