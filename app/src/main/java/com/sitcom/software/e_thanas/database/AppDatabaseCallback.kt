package com.sitcom.software.e_thanas.database

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sitcom.software.e_thanas.entities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class AppDatabaseCallback(private val scope: CoroutineScope, private val database: AppDatabase) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        scope.launch {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")

            // Insérez des données dans la table Cimtiere
            database.cimetiereDao().insert(Cimetiere(cimetiere = 1, CP = 12345, rue = "Rue du Cimetière", ville = "Ville du Cimetière"))

            // Insérez des données dans la table type_sepulture
            database.typeSepultureDao().insert(TypeSepulture(id_type = 1, nom = "Type 1"))
            database.typeSepultureDao().insert(TypeSepulture(id_type = 2, nom = "Type 2"))

            // Insérez des données dans la table Utilisateur
            database.utilisateurDao().insert(Utilisateur(id_utilisateur = 1, nom = "Nom", prenom = "Prénom", mail = "email@example.com", mdp = "motdepasse"))

            // Insérez des données dans la table Sepulture
            database.sepultureDao().insert(Sepulture(id_sep = 1, cimetiere = 1, id_type = 1))

            // Insérez des données dans la table Defunts
            database.defuntsDao().insert(Defunts(id_defunts = 1, nom = "Nom", nom_jeune_fille = "NomJeuneFille", prenom = "Prénom", date_naiss = dateFormat.parse("1990-01-01"), date_deces = dateFormat.parse("2020-01-01"), id_sep = 1))

            // Insérez des données dans la table Favoris
            database.favorisDao().insert(Favoris(id_defunts = 1, id_utilisateur = 1))
        }
    }
}
