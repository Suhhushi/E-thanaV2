package com.sitcom.software.e_thanas.ui.Search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
//lol
class SearchViewModel : ViewModel() {
    private val _isFieldVisible = MutableLiveData<Boolean>()
    val isFieldVisible: LiveData<Boolean> = _isFieldVisible

    fun handleSpinnerSelection(selectedItem: String) {
        _isFieldVisible.value = selectedItem == "Femme"
    }
}