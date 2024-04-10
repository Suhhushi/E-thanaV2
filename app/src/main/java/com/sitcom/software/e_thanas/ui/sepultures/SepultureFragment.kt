package com.sitcom.software.e_thanas.ui.sepultures

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sitcom.software.e_thanas.R
import com.sitcom.software.e_thanas.ui.cimetieres.CimetieresFragment
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class SepultureFragment : Fragment() {

    private lateinit var viewModel: SepultureViewModel
    private lateinit var mMap: MapView
    private lateinit var mMyLocationOverlay: MyLocationNewOverlay
    private lateinit var loadingProgressBar: ProgressBar

    private lateinit var sepultureLoc: GeoPoint

    private val centreFrance = GeoPoint(46.777036, 2.450763)

    private var coordX: Double = 0.0
    private var coordY: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sepulture, container, false)
        mMap = view.findViewById(R.id.osmmap)
        loadingProgressBar = view.findViewById(R.id.loadingProgressBar)

        Configuration.getInstance().load(
            requireContext(),
            requireActivity().getSharedPreferences("MyPrefs", 0)
        )

        mMap.setTileSource(TileSourceFactory.MAPNIK)
        mMap.setMultiTouchControls(true)

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                CimetieresFragment.PERMISSION_REQUEST_CODE
            )
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SepultureViewModel::class.java)
        viewModel.getDefunts(requireContext())

        Handler().postDelayed({
            mMap.visibility = View.VISIBLE // Afficher la carte après 5 secondes
            loadingProgressBar.visibility = View.GONE // Masquer la vue de chargement après 5 secondes
        }, 3000)

        loadingProgressBar.visibility = View.VISIBLE // Afficher la vue de chargement

        viewModel.defunts.observe(viewLifecycleOwner, Observer { defunts ->
            val defuntId = arguments?.getInt("id_defunt")
            val defunt = defunts.find { it.id == defuntId }

            if (defunt != null) {
                val textViewNomJF = view.findViewById<TextView>(R.id.textViewNomJF)
                val textViewPrenom = view.findViewById<TextView>(R.id.textViewPrenom)
                val textViewNom = view.findViewById<TextView>(R.id.textViewNom)

                textViewNomJF.visibility = View.GONE
                textViewPrenom.text = defunt.prenom
                textViewNom.text = defunt.nom

                if (defunt.nomJeuneFille != "NULL") {
                    textViewNomJF.text = defunt.nomJeuneFille
                    textViewNomJF.visibility = View.VISIBLE
                }

                viewModel.getSepulture(requireContext())
                viewModel.sepulture.observe(viewLifecycleOwner, Observer { sepultures ->
                    val sepulture = sepultures.find { it.id == defunt.idSepulture}

                    coordX = sepulture?.coordX?.toDouble()!!
                    coordY = sepulture?.coordY?.toDouble()!!

                    val mIcon = resources.getDrawable(R.drawable.baseline_location_pin_24)
                    val sepultureLoc = GeoPoint(coordX, coordY)

                    setupMap(sepultureLoc) // Passer sepultureLoc à setupMap()

                    val marker1 = Marker(mMap)
                    marker1.position = sepultureLoc
                    marker1.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    mMap.overlays.add(marker1)
                    marker1.icon = mIcon
                    marker1.title = "Localisation de la sépulture de ${defunt.prenom} ${defunt.nom}"

                    marker1.setOnMarkerClickListener { _, _ ->
                        marker1.showInfoWindow()
                        true
                    }

                    viewModel.getCimetieres(requireContext())
                    viewModel.cimetieres.observe(viewLifecycleOwner, Observer { cimetieres ->
                        val cimetiere = cimetieres.find { it.id == sepulture?.idCimetiere }

                        view.findViewById<TextView>(R.id.textViewVille).text = cimetiere?.ville
                        view.findViewById<TextView>(R.id.textViewCimetiere).text = cimetiere?.nom
                    })
                })
            } else {
                Log.e("SepultureFragment", "Défunt non trouvé avec l'ID $defuntId")
            }
        })

        val backButton: ImageButton = view.findViewById(R.id.btnBack)
        backButton.setOnClickListener {
            onBackButtonClicked(it)
        }

        val favButtonImg: ImageButton = view.findViewById(R.id.btnFav)
        favButtonImg.setOnClickListener{
            onFavButtonClicked(it)
        }

        val returnToSearchButton: ImageButton = view.findViewById(R.id.btnRetourSearch)
        returnToSearchButton.setOnClickListener {
            onReturnToSearchButtonClicked(it)
    }


}


    private fun setupMap(sepultureLoc: GeoPoint) {
        mMyLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(requireContext()), mMap)
        mMyLocationOverlay.enableMyLocation()
        mMyLocationOverlay.enableFollowLocation()
        mMap.overlays.add(mMyLocationOverlay)

        mMap.controller.setZoom(17.4)
        mMap.controller.animateTo(sepultureLoc) // Déplacer la carte vers la position du marqueur
        mMap.controller.zoomTo(17.4, null) // Appliquer un niveau de zoom adapté
    }

    private fun onBackButtonClicked(view: View) {
        findNavController().popBackStack()
        findNavController().popBackStack()
    }

    private fun onReturnToSearchButtonClicked(view: View) {
        findNavController().popBackStack()
        findNavController().popBackStack()
    }


    private fun onFavButtonClicked(it: View?) {
        // Placeholder for favorite button click handling
    }
}
