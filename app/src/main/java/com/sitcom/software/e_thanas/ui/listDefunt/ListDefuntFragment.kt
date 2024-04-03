package com.sitcom.software.e_thanas.ui.listDefunt

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sitcom.software.e_thanas.R
import com.sitcom.software.e_thanas.classes.Cimetiere
import com.sitcom.software.e_thanas.classes.Sepulture
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

class ListDefuntFragment : Fragment() {

    private lateinit var viewModel: ListDefuntViewModel
    private var cimetiereTrouve: Cimetiere? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_listdefunt, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialisez votre ViewModel ici
        viewModel = ViewModelProvider(this).get(ListDefuntViewModel::class.java)

        // Appel de la fonction pour récupérer tous les défunts
        viewModel.getDefunts(requireContext())

        // Appel de la fonction pour récupérer toutes les sépultures
        viewModel.getSepulture(requireContext())

        // Appel de la fonction pour récupérer tous les cimetières
        viewModel.getCimetieres(requireContext())

        // Observer les changements de la LiveData contenant la liste des défunts
        viewModel.defunts.observe(viewLifecycleOwner, Observer { defunts ->
            // Récupérer les données passées par le Bundle
            val nom = arguments?.getString("nom")
            val prenom = arguments?.getString("prenom")
            val nomJF = arguments?.getString("nomJF")
            val genre = arguments?.getString("genre")
            val ville = arguments?.getString("ville")
            val cimetiereNom = arguments?.getString("Cimetiere")

            // Filtrer les défunts en fonction des critères de recherche
            var defuntsFiltres = defunts.filter { defunt ->
                (nom.isNullOrBlank() || defunt.nom.equals(nom, ignoreCase = true)) &&
                        (prenom.isNullOrBlank() || defunt.prenom.equals(prenom, ignoreCase = true)) &&
                        (nomJF.isNullOrBlank() || defunt.nomJeuneFille.equals(nomJF, ignoreCase = true)) &&
                        (genre == "Civilité" || defunt.sexe.equals(genre, ignoreCase = true)) &&
                        (defunt.id != 0)
            }

            // Si aucun résultat n'est trouvé, utilisez une liste non filtrée
            if (defuntsFiltres.isEmpty()) {
                defuntsFiltres = defunts.filter { defunt ->
                    (prenom.isNullOrBlank() || defunt.prenom.equals(prenom, ignoreCase = true)) &&
                            (defunt.id != 0)
                }
            }

            // Observer les changements de la LiveData contenant la liste des cimetières
            viewModel.cimetieres.observe(viewLifecycleOwner, Observer { cimetieres ->
                // Trouver le cimetière correspondant au nom spécifié
                cimetiereTrouve = cimetieres.find { it.nom.equals(cimetiereNom, ignoreCase = true) }

                Log.d("ListDefuntFragment", "$cimetiereTrouve")


                cimetiereTrouve?.let { cimetiere ->
                    // Observer les changements de la LiveData contenant la liste des sépultures
                    viewModel.sepulture.observe(viewLifecycleOwner, Observer { sepultures ->
                        // Filtrer les sépultures pour celles qui appartiennent au cimetière trouvé
                        val sepulturesDansCimetiere = sepultures.filter { it.idCimetiere == cimetiereTrouve?.id }

                        // Filtrer les défunts en fonction des sépultures trouvées dans le cimetière
                        defuntsFiltres = defuntsFiltres.filter { defunt ->
                            sepulturesDansCimetiere.any { it.id == defunt.idSepulture }
                        }

                        // Initialisez votre RecyclerView
                        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewDefunts)
                        recyclerView.layoutManager = LinearLayoutManager(requireContext())

                        // Créez l'adaptateur pour votre RecyclerView
                        val adapter = DefuntAdapter(defuntsFiltres)
                        recyclerView.adapter = adapter
                    })

                }
            })


            // Initialisez votre RecyclerView
            val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewDefunts)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            // Créez l'adaptateur pour votre RecyclerView
            val adapter = DefuntAdapter(defuntsFiltres)
            recyclerView.adapter = adapter
        })


        // Récupérer les données passées par le Bundle
        val nom = arguments?.getString("nom")
        val prenom = arguments?.getString("prenom")


        // Utilisez les données récupérées comme vous le souhaitez
        Log.d("ListDefuntFragment", "Nom: $nom, Prenom: $prenom")

        // Mettez en place votre RecyclerView ou d'autres éléments de votre fragment ici

        // Trouvez le bouton de retour par son ID
        val backButton: ImageButton = view.findViewById(R.id.btnBack)

        // Définissez le OnClickListener pour le bouton de retour
        backButton.setOnClickListener {
            // Appel de la fonction onBackButtonClicked lorsque le bouton est cliqué
            onBackButtonClicked(it)
        }

    }

    // La fonction pour gérer le clic sur le bouton de retour
    fun onBackButtonClicked(view: View) {
        findNavController().navigateUp()
    }
}