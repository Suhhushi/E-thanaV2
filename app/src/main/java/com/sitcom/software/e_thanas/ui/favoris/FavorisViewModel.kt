package com.sitcom.software.e_thanas.ui.favoris

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sitcom.software.e_thanas.classes.Defunt
import com.sitcom.software.e_thanas.classes.Enregistrement
import com.sitcom.software.e_thanas.classes.Sepulture
import com.sitcom.software.e_thanas.parser.XmlParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavorisViewModel : ViewModel() {

    private val _enregistrement = MutableLiveData<List<Enregistrement>>()
    val enregistrement: LiveData<List<Enregistrement>>
        get() = _enregistrement

    private val _defunts = MutableLiveData<List<Defunt>>()
    val defunts: LiveData<List<Defunt>>
        get() = _defunts

    private val _sepulture = MutableLiveData<List<Sepulture>>()
    val sepulture: LiveData<List<Sepulture>>
        get() = _sepulture

    fun getEnregistrement(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            val enregistrementList = XmlParser.parseXml(context).flatMap { it.enregistrement }
            _enregistrement.postValue(enregistrementList)
        }
    }

    fun getDefunts(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            val defuntsList = XmlParser.parseXml(context).flatMap { it.defunts }
            _defunts.postValue(defuntsList)
        }
    }

    fun getSepulture(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            val sepultureList = XmlParser.parseXml(context).flatMap { it.sepulture }
            _sepulture.postValue(sepultureList)
        }
    }
}