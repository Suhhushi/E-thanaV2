package com.sitcom.software.e_thanas.ui.listDefunt

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sitcom.software.e_thanas.R

class ListDefuntFragment : Fragment() {

    private lateinit var viewModel: ListDefuntViewModel

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

        // Observer les changements de la LiveData contenant la liste des défunts
        viewModel.defunts.observe(viewLifecycleOwner, Observer { defunts ->
            // Afficher la liste des défunts dans le logcat
            val nom = arguments?.getString("nom")
            val prenom = arguments?.getString("prenom")

            val defuntsFiltres = defunts.filter { defunt ->
                (defunt.nom == nom || defunt.prenom == prenom) && defunt.id != 0
            }

            for (defunt in defuntsFiltres) {
                Log.d("ListDefuntFragment", "Defunt: $defunt")
                // Afficher ou manipuler les défunts filtrés comme nécessaire

            }
            // Initialisez votre RecyclerView
            val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewDefunts)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            // Créez l'adaptateur pour votre RecyclerView
            val adapter = DefuntAdapter(defuntsFiltres) // Utilisez la liste filtrée de défunts
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


