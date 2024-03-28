package com.sitcom.software.e_thanas.ui.sepultures

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sitcom.software.e_thanas.R
import com.sitcom.software.e_thanas.classes.Sepulture
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

    private val centreFrance = GeoPoint(46.777036, 2.450763)
    private val cimetiereCroixDaurade = GeoPoint(43.640124, 1.461586)


    private var coordX : Double = 0.0
    private var coordY : Double = 0.0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { val view = inflater.inflate(R.layout.fragment_sepulture, container, false)

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
                CimetieresFragment.PERMISSION_REQUEST_CODE
            )
        } else {

            // Autorisations de localisation déjà accordées
            setupMap()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialisez votre ViewModel ici
        viewModel = ViewModelProvider(this).get(SepultureViewModel::class.java)

        viewModel.getDefunts(requireContext())

        // Observer pour écouter les changements dans la liste des défunts
        viewModel.defunts.observe(viewLifecycleOwner, Observer { defunts ->
            // Récupérer l'ID du défunt depuis les arguments
            val defuntId = arguments?.getInt("id_defunt")

            Log.d("SepultureFragment", "ID du défunt: $defuntId")

            // Récupérer le défunt correspondant à l'ID
            val defunt = defunts.find { it.id == defuntId }

            if (defunt != null) {
                // Afficher les détails du défunt dans le logcat
                Log.d("SepultureFragment", "Défunt trouvé : $defunt")

                val textViewNomJF = view.findViewById<TextView>(R.id.textViewNomJF)
                val textViewPrenom = view.findViewById<TextView>(R.id.textViewPrenom)
                val textViewNom = view.findViewById<TextView>(R.id.textViewNom)

                // Assurez-vous que textViewNomJF est initialisé en tant que Gone si nécessaire
                textViewNomJF.visibility = View.GONE

                textViewPrenom.text = defunt.prenom
                textViewNom.text = defunt.nom
                if (defunt.nomJeuneFille != "NULL") {
                    textViewNomJF.text = defunt.nomJeuneFille
                    textViewNomJF.visibility = View.VISIBLE
                }


                // Récupérer et afficher la liste des sépultures dans le logcat
                viewModel.getSepulture(requireContext())
                viewModel.sepulture.observe(viewLifecycleOwner, Observer { sepultures ->

                    val sepulture = sepultures.find { it.id == defunt.idSepulture}
                    Log.d("SepultureFragment", "La sépultures : $sepulture")

                    coordX = sepulture?.coordX?.toDouble()!! //pour la map
                    coordY = sepulture?.coordY?.toDouble()!!

                    //Changement du modèle du marker de cimetière
                    val mIcon = resources.getDrawable(R.drawable.baseline_location_pin_24)

                    var sepultureLoc = GeoPoint(coordX, coordY)
                    mMap.controller.animateTo(sepultureLoc)

                    // Ajouter un marqueur pour le cimetière de Croix Daurade
                    val marker1 = Marker(mMap)

                    marker1.position = sepultureLoc
                    marker1.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    mMap.overlays.add(marker1)
                    marker1.icon = mIcon


                    marker1.setOnMarkerClickListener { _, _ ->

                        marker1.showInfoWindow()
                        true
                    }



                    Log.d("SepultureFragment", "La coordonnées : $coordX , $coordY")



                    // Récupérer et afficher la liste des cimetières dans le logcat
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

        // Trouvez le bouton de retour par son ID
        val backButton: ImageButton = view.findViewById(R.id.btnBack)
        val favButtonImg : ImageButton = view.findViewById(R.id.btnFav)

        // Définissez le OnClickListener pour le bouton de retour
        backButton.setOnClickListener {
            // Appel de la fonction onBackButtonClicked lorsque le bouton est cliqué
            onBackButtonClicked(it)
        }

        favButtonImg.setOnClickListener{
            onFavButtonClicked(it)
    }





    }



    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupMap() {

        // Ajouter l'overlay de la localisation de l'utilisateur
        mMyLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(requireContext()), mMap)
        mMyLocationOverlay.enableMyLocation()
        mMyLocationOverlay.enableFollowLocation()
        mMap.overlays.add(mMyLocationOverlay)
        mMap.controller.setZoom(17.6)
        //Faire en sorte que l'icone soit plus grande



        mMyLocationOverlay.runOnFirstFix {
            requireActivity().runOnUiThread {
                val userLocation = mMyLocationOverlay.myLocation

            }
        }

    }


    fun onBackButtonClicked(view: View) {
        findNavController().navigateUp()
    }

    private fun onFavButtonClicked(it: View?) {


    }


}



