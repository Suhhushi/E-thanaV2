package com.sitcom.software.e_thanas.ui.cimetieres

import android.Manifest
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
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class CimetieresFragment : Fragment() {

    //commit
    private lateinit var mMap: MapView
    private lateinit var mMyLocationOverlay: MyLocationNewOverlay
    private val centreFrance = GeoPoint(46.777036, 2.450763)
    private val cimetiereCroixDaurade = GeoPoint(43.640124, 1.461586)

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

    private fun setupMap() {
        // Ajouter l'overlay de la localisation de l'utilisateur
        mMyLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(requireContext()), mMap)
        mMyLocationOverlay.enableMyLocation()
        mMyLocationOverlay.enableFollowLocation()
        mMap.overlays.add(mMyLocationOverlay)
        mMap.controller.animateTo(centreFrance)
        mMap.controller.setZoom(6.7)
        mMap.controller.animateTo(cimetiereCroixDaurade)


        // Centrer la carte sur la position de l'utilisateur
        // Zoom sur la position de l'utilisateur lorsque la première position fixée est obtenue
        mMyLocationOverlay.runOnFirstFix {
            requireActivity().runOnUiThread {
                mMap.controller.animateTo(mMyLocationOverlay.myLocation)
                mMap.controller.setZoom(16.0)

            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
    }
}

