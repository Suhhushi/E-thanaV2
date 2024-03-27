package com.sitcom.software.e_thanas.ui.listDefunt

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sitcom.software.e_thanas.classes.Defunt
import com.sitcom.software.e_thanas.parser.XmlParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListDefuntViewModel : ViewModel() {

    private val _defunts = MutableLiveData<List<Defunt>>()
    val defunts: LiveData<List<Defunt>>
        get() = _defunts

    fun getDefunts(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            val defuntsList = XmlParser.parseXml(context).flatMap { it.defunts }
            _defunts.postValue(defuntsList)
        }
    }

}
