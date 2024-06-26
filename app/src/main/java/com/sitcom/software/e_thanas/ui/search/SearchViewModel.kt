package com.sitcom.software.e_thanas.ui.search

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sitcom.software.e_thanas.classes.Cimetiere
import com.sitcom.software.e_thanas.parser.XmlParser
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.Normalizer
import java.util.Locale

class SearchViewModel : ViewModel() {
    private val dataSexe: MutableLiveData<List<String>> = MutableLiveData()
    private val dataCimetiereName: MutableLiveData<List<String>> = MutableLiveData()
    private val dataCimetiereVille: MutableLiveData<List<String>> = MutableLiveData()
    // Liste pour stocker les données du fichier XML
    private val cimetieresList = mutableListOf<Cimetiere>()
    // LiveData pour observer les changements dans les données
    private val _cimetieresLiveData = MutableLiveData<List<Cimetiere>>()

    @OptIn(DelicateCoroutinesApi::class)
    fun getCimetiereName(context: Context): LiveData<List<String>> {
        GlobalScope.launch(Dispatchers.IO) {
            val cimetieres = XmlParser.parseXml(context)
            // Clear existing data before adding new data
            cimetieresList.clear()
            // Add new data to the list
            cimetieresList.addAll(cimetieres)
            // Update LiveData with the new data
            _cimetieresLiveData.postValue(cimetieresList)

            // Extract cimetiere names and update dataCimetiereName
            val cimetiereNames = mutableListOf<String>()
            for (cimetiere in cimetieresList) {
                cimetiereNames.add(cimetiere.nom)
            }
            dataCimetiereName.postValue(cimetiereNames)
        }

        return dataCimetiereName
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getCimetiereVille(context: Context): LiveData<List<String>> {
        GlobalScope.launch(Dispatchers.IO) {
            val cimetieres = XmlParser.parseXml(context)
            // Clear existing data before adding new data
            cimetieresList.clear()
            // Add new data to the list
            cimetieresList.addAll(cimetieres)
            // Update LiveData with the new data
            _cimetieresLiveData.postValue(cimetieresList)

            // Extract unique cimetiere cities and update dataCimetiereVille
            val uniqueCimetiereVilles = cimetieresList.map { it.ville }.toSet().toList()
            dataCimetiereVille.postValue(uniqueCimetiereVilles)
        }
        return dataCimetiereVille
    }

    fun getData(): LiveData<List<String>> {
            // Ajoutez "homme" et "femme" à la liste de données
            val dataList = listOf("Civilité","Monsieur", "Madame")
            dataSexe.value = dataList

            return dataSexe
        }

    fun removeAccents(input: String): String {
        val normalizedString = Normalizer.normalize(input, Normalizer.Form.NFD)
        return normalizedString.replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")
    }

    fun normalizeInput(input: String, capitalizeFirstName: Boolean = false): String {
        // Retirer les accents
        var normalizedInput = removeAccents(input)
        // Convertir en majuscules et supprimer les espaces supplémentaires
        normalizedInput = normalizedInput.trim().uppercase()
        // Capitaliser la première lettre du prénom si nécessaire
        if (capitalizeFirstName && normalizedInput.isNotEmpty()) {
            normalizedInput = normalizedInput.substring(0, 1) + normalizedInput.substring(1).toLowerCase()
        }
        return normalizedInput
    }


}