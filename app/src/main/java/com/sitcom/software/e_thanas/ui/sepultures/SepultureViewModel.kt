package com.sitcom.software.e_thanas.ui.sepultures

import android.content.Context
import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sitcom.software.e_thanas.R
import com.sitcom.software.e_thanas.classes.Cimetiere
import com.sitcom.software.e_thanas.classes.Defunt
import com.sitcom.software.e_thanas.classes.Sepulture
import com.sitcom.software.e_thanas.parser.XmlParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline
import org.xmlpull.v1.XmlPullParser
import java.io.File
import java.io.FileOutputStream


class SepultureViewModel : ViewModel() {

    private val _defunts = MutableLiveData<List<Defunt>>()
    val defunts: LiveData<List<Defunt>>
        get() = _defunts

    private val _sepulture = MutableLiveData<List<Sepulture>>()
    val sepulture: LiveData<List<Sepulture>>
        get() = _sepulture

    private val _cimetieres = MutableLiveData<List<Cimetiere>>()
    val cimetieres: LiveData<List<Cimetiere>>
        get() = _cimetieres


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

    fun getCimetieres(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            val cimetieresList = XmlParser.parseXml(context)
            _cimetieres.postValue(cimetieresList)
        }
    }

    fun drawRoute(mMap : MapView, startPoint: GeoPoint, endPoint: GeoPoint) {
        val polyline = Polyline()
        polyline.color = Color.BLUE
        polyline.width = 5f

        // Ajoutez les points de départ et d'arrivée à la ligne
        polyline.addPoint(startPoint)
        polyline.addPoint(endPoint)

        // Ajoutez la ligne à la carte
        mMap.overlays.add(polyline)

        // Appelez invalidate pour redessiner la carte avec la ligne ajoutée
        mMap.invalidate()
    }

}