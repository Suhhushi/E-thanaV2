package com.sitcom.software.e_thanas.ui.cimetieres

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.sitcom.software.e_thanas.R
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class CimetieresFragment : Fragment() {

    //commit
    private lateinit var mMap: MapView
    private lateinit var mMyLocationOverlay: MyLocationNewOverlay
    private val centreFrance = GeoPoint(46.777036, 2.450763)
    private val cimetiereCroixDaurade = GeoPoint(43.640124, 1.461586)
    private val cimetiereTerreCabade = GeoPoint(43.608111, 1.462842)
    private val cimetiereRapas = GeoPoint(43.591679, 1.428226)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cimetieres, container, false)

        mMap = view.findViewById(R.id.osmmap)

        // Initialisation de la configuration d'OSMDroid
        Configuration.getInstance().load(
            requireContext(),
            requireActivity().getSharedPreferences("MyPrefs", 0)
        )

        // Configuration de la carte
        mMap.setTileSource(TileSourceFactory.MAPNIK)
        mMap.setMultiTouchControls(true)

        // Vérifier les autorisations de localisation
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Demander les autorisations de localisation si elles ne sont pas accordées
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PERMISSION_REQUEST_CODE
            )
        } else {
            // Autorisations de localisation déjà accordées
            setupMap()
        }

        return view
    }



    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupMap() {
        // Ajouter l'overlay de la localisation de l'utilisateur
        mMyLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(requireContext()), mMap)
        mMyLocationOverlay.enableMyLocation()
        mMyLocationOverlay.enableFollowLocation()
        mMap.overlays.add(mMyLocationOverlay)
        mMap.controller.animateTo(centreFrance)
        mMap.controller.setZoom(6.2)
        //Changement du modèle du marker de cimetière
        val mIcon = resources.getDrawable(R.drawable.baseline_location_pin_24)
        //Faire en sorte que l'icone soit plus grande



        mMyLocationOverlay.runOnFirstFix {
            requireActivity().runOnUiThread {
                val userLocation = mMyLocationOverlay.myLocation
                mMap.controller.animateTo(userLocation)
                mMap.controller.setZoom(13.0) // Zoom level 15.0 (you can adjust as needed)
            }
        }

        // Ajouter un marqueur pour le cimetière de Croix Daurade
        val marker1 = Marker(mMap)

        marker1.position = cimetiereCroixDaurade
        marker1.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        mMap.overlays.add(marker1)
        marker1.icon = mIcon
        marker1.title = "Cimetière de Croix Daurade"
        marker1.snippet = "Imp. du Cimetière de Croix Daurade, 31200 Toulouse"
        //Marquer les horaires d'ouverture
        marker1.subDescription  = "Horaire d'ouverture : 8h00-18h00 7/7"
        marker1.image = resources.getDrawable(R.drawable.croix_daurade)

        marker1.setOnMarkerClickListener { _, _ ->
            mMap.controller.animateTo(cimetiereCroixDaurade)
            mMap.controller.setZoom(13.0)
            marker1.showInfoWindow()
            true
        }


        // Ajouter un marqueur pour le cimetière de Terre Cabade
        val marker2 = Marker(mMap)
        marker2.position = cimetiereTerreCabade
        marker2.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        mMap.overlays.add(marker2)
        marker2.icon = mIcon
        marker2.title = "Cimetière de Terre Cabade"
        marker2.snippet = "Avenue de Terre Cabade, 31100 Toulouse"
        marker2.subDescription  = "Horaire d'ouverture : 8h00-18h00 7/7"

        marker2.image = resources.getDrawable(R.drawable.terre_cabade)

        marker2.setOnMarkerClickListener { _, _ ->
            mMap.controller.animateTo(cimetiereTerreCabade)
            mMap.controller.setZoom(13.0)
            marker2.showInfoWindow()

            true
        }



        // Ajouter un marqueur pour le cimetière de Rapas
        val marker3 = Marker(mMap)
        marker3.position = cimetiereRapas
        marker3.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        mMap.overlays.add(marker3)
        marker3.icon = mIcon
        marker3.title = "Cimetière de Rapas"
        marker3.snippet = "63 Chem. de la Néboude, 31300 Toulouse"
        marker3.subDescription  = "Horaire d'ouverture : 8h00-18h00 7/7"
        marker3.image = resources.getDrawable(R.drawable.rapas)

        marker3.setOnMarkerClickListener { _, _ ->
            mMap.controller.animateTo(cimetiereRapas)
            mMap.controller.setZoom(13.0)
            marker3.showInfoWindow()

            true
        }

        // Centrer la carte sur la position de l'utilisateur
        // Zoom sur la position de l'utilisateur lorsque la première position fixée est obtenue
        mMyLocationOverlay.runOnFirstFix {
            requireActivity().runOnUiThread {
                mMap.controller.animateTo(mMyLocationOverlay.myLocation)


            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
    }
}

