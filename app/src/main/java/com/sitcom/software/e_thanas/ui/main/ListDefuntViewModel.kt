package com.sitcom.software.e_thanas.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sitcom.software.e_thanas.classes.Cimetiere
import com.sitcom.software.e_thanas.classes.Defunt
import com.sitcom.software.e_thanas.parser.XmlParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListDefuntViewModel : ViewModel() {
    private val _defuntLiveData = MutableLiveData<List<Defunt>>()

    fun getDefunts(context: Context): LiveData<List<Defunt>> {
        GlobalScope.launch(Dispatchers.IO) {
            val defunts = XmlParser.parseDefunts(context)
            _defuntLiveData.postValue(defunts)
        }
        return _defuntLiveData
    }
}