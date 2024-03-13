package com.sitcom.software.e_thanas.donnees

import java.sql.DriverManager
import com.sitcom.software.e_thanas.outils.Serializer
import com.sitcom.software.e_thanas.classes.Cimetiere



abstract class CimetiereRepository(){

    fun main() {
        val url = "jdbc:mysql://localhost:8888/bd_ethana"
        val username = "root"
        val password = ""

        try {
            // Establish a connection to the database
            val connection = DriverManager.getConnection(url, username, password)

            // Create a statement
            val statement = connection.createStatement()

            // Execute a query
            val query = "SELECT * FROM cimetieres"
            val resultSet = statement.executeQuery(query)

            // Process the result set
            while (resultSet.next()) {
                val idCimetiere = resultSet.getInt("idCimetiere")
                val nomCimetiere = resultSet.getString("nomCimetiere")
                val rue = resultSet.getInt("rue")
                val ville = resultSet.getString("ville")
                val cp = resultSet.getString("cp")

                // Do something with the retrieved data
                println("ID: $idCimetiere, Nom: $nomCimetiere, Rue: $rue, Ville: $ville, CP: $cp")
            }

            // Close the resources
            resultSet.close()
            statement.close()
            connection.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}