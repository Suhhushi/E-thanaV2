package com.sitcom.software.e_thanas.outils

import android.content.Context
import java.io.FileNotFoundException
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

abstract class Serializer (){



    companion object leSerialiser {
        /**
         * @param filename : fichier d'enregistrment du client en binaire
         * @param object   objet à sérialiser (ici le client)
         * @param context  context de l'application
         */

        fun serialize(filename: String?, `object`: Any?, context: Context) {

            try {
                //ouverture du fichier en écriture
                val file = context.openFileOutput(filename, Context.MODE_PRIVATE)
                //déclaration d'un flux d'écriture
                val oos: ObjectOutputStream
                try {
                    //ouverture du flux d'écriture vers le fichier
                    oos = ObjectOutputStream(file)
                    //écriture de l'objet dans le fichier
                    oos.writeObject(`object`)
                    //enregistrement
                    oos.flush()
                    //fermeture du flux
                    oos.close()
                } catch (e: IOException) {
                    //erreur de sérialisation
                    e.printStackTrace()
                }
            } catch (e: FileNotFoundException) {
                //fichier non trouvé
                e.printStackTrace()
            } catch (e: IOException) {
                //autres exceptions
                e.printStackTrace()
            }

        }


        /**
         *
         * @param filename : fichier d'enregistrment du client en binaire
         * @param context context de l'application
         */
       fun deSerialize(filename: String?, context: Context): Any? {
//déclaration de l'objet retour et initialisation
            var `object`: Any? = null
            try {
                //ouverture du fichier en lecture
                val file = context.openFileInput(filename)
                val ois: ObjectInputStream
                try {
                    //ouverture du flux de lecture vers le fichier
                    ois = ObjectInputStream(file)
                    //lecture de l'objet serialisé
                    `object` = ois.readObject()
                    //fermeture du flux
                    ois.close()
                    //gestion des exceptions
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            return `object`
        }


    }
}