package com.sitcom.software.e_thanas.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sitcom.software.e_thanas.classes.Cimetiere
import com.sitcom.software.e_thanas.parser.XmlParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    // Liste pour stocker les données du fichier XML
    private val cimetieresList = mutableListOf<Cimetiere>()

    // LiveData pour observer les changements dans les données
    private val _cimetieresLiveData = MutableLiveData<List<Cimetiere>>()
    val cimetieresLiveData get() = _cimetieresLiveData

    // Fonction pour charger les données du fichier XML
    fun loadXmlData(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            val cimetieres = XmlParser.parseXml(context)
            // Ajouter les données à la liste
            cimetieresList.addAll(cimetieres)
            // Mettre à jour le LiveData avec les nouvelles données
            _cimetieresLiveData.postValue(cimetieresList)
        }
    }
}
