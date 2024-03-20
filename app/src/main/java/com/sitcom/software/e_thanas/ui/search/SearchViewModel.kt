package com.sitcom.software.e_thanas.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    private val data: MutableLiveData<List<String>> = MutableLiveData()

    fun getData(): LiveData<List<String>> {
        // Ajoutez "homme" et "femme" à la liste de données
        val dataList = listOf("Homme", "Femme")
        data.value = dataList
        return data
    }


}